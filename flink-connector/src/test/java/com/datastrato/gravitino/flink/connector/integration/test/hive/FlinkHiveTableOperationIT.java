/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.integration.test.hive;

import static com.datastrato.gravitino.rel.expressions.transforms.Transforms.EMPTY_TRANSFORM;

import com.datastrato.gravitino.Catalog;
import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.flink.connector.integration.test.utils.TestUtils;
import com.datastrato.gravitino.rel.Column;
import com.datastrato.gravitino.rel.Table;
import com.datastrato.gravitino.rel.expressions.transforms.Transform;
import com.datastrato.gravitino.rel.expressions.transforms.Transforms;
import com.datastrato.gravitino.rel.types.Types;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.ResultKind;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.catalog.CatalogBaseTable;
import org.apache.flink.table.catalog.CatalogTable;
import org.apache.flink.table.catalog.ObjectPath;
import org.apache.flink.table.catalog.ResolvedCatalogBaseTable;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FlinkHiveTableOperationIT extends FlinkHiveCatalogBaseIT {
  @BeforeAll
  static void startHiveTableEnv() {
    initDefaultHiveSchema();
  }

  @AfterAll
  static void stopHiveTableEnv() {
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          if (catalog.asSchemas().schemaExists(defaultHiveSchema)) {
            catalog.asSchemas().dropSchema(defaultHiveSchema, true);
          }
        });
  }

  @Test
  public void testCreateNoPartitionTable() {
    String tableName, comment;
    tableName = "test_create_no_partition_table";
    comment = "test comment";
    String key = "test key";
    String value = "test value";

    // 1. The NOT NULL constraint for column is only supported since Hive 3.0,
    // but the current Gravitino Hive catalog only supports Hive 2.x.
    // 2. Hive doesn't support Time and Timestamp with timezone type.
    // 3. Flink SQL only support to create Interval Month and Second(3).
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(string_type STRING COMMENT 'string_type', "
                      + " double_type DOUBLE COMMENT 'double_type',"
                      + " int_type INT COMMENT 'int_type',"
                      + " varchar_type VARCHAR COMMENT 'varchar_type',"
                      + " char_type CHAR COMMENT 'char_type',"
                      + " boolean_type BOOLEAN COMMENT 'boolean_type',"
                      + " byte_type BINARY COMMENT 'byte_type',"
                      + " binary_type BINARY(10) COMMENT 'binary_type',"
                      + " decimal_type DECIMAL(10, 2) COMMENT 'decimal_type',"
                      + " bigint_type BIGINT COMMENT 'bigint_type',"
                      + " float_type FLOAT COMMENT 'float_type',"
                      + " date_type DATE COMMENT 'date_type',"
                      + " timestamp_type TIMESTAMP COMMENT 'timestamp_type',"
                      + " smallint_type SMALLINT COMMENT 'smallint_type',"
                      + " array_type ARRAY<INT> COMMENT 'array_type',"
                      + " map_type MAP<INT, STRING> COMMENT 'map_type',"
                      + " struct_type ROW<k1 INT, k2 String>)"
                      + " COMMENT '%s' WITH ("
                      + "'%s' = '%s')",
                  tableName, comment, key, value);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          Table table =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName));
          Assertions.assertNotNull(table);
          Assertions.assertEquals(comment, table.comment());
          Assertions.assertEquals(value, table.properties().get(key));
          Column[] columns =
              new Column[] {
                Column.of("string_type", Types.StringType.get(), "string_type", true, false, null),
                Column.of("double_type", Types.DoubleType.get(), "double_type"),
                Column.of("int_type", Types.IntegerType.get(), "int_type"),
                Column.of("varchar_type", Types.StringType.get(), "varchar_type"),
                Column.of("char_type", Types.FixedCharType.of(1), "char_type"),
                Column.of("boolean_type", Types.BooleanType.get(), "boolean_type"),
                Column.of("byte_type", Types.ByteType.get(), "byte_type"),
                Column.of("binary_type", Types.BinaryType.get(), "binary_type"),
                Column.of("decimal_type", Types.DecimalType.of(10, 2), "decimal_type"),
                Column.of("bigint_type", Types.LongType.get(), "bigint_type"),
                Column.of("float_type", Types.FloatType.get(), "float_type"),
                Column.of("date_type", Types.DateType.get(), "date_type"),
                Column.of(
                    "timestamp_type", Types.TimestampType.withoutTimeZone(), "timestamp_type"),
                Column.of("smallint_type", Types.ShortType.get(), "smallint_type"),
                Column.of(
                    "array_type", Types.ListType.of(Types.IntegerType.get(), true), "array_type"),
                Column.of(
                    "map_type",
                    Types.MapType.of(Types.IntegerType.get(), Types.StringType.get(), true),
                    "map_type"),
                Column.of(
                    "struct_type",
                    Types.StructType.of(
                        Types.StructType.Field.nullableField("k1", Types.IntegerType.get()),
                        Types.StructType.Field.nullableField("k2", Types.StringType.get())),
                    null)
              };
          assertColumns(columns, table.columns());
          Assertions.assertArrayEquals(EMPTY_TRANSFORM, table.partitioning());
        });
  }

  @Test
  public void testCreatePartitionTable() {
    String tableName = "test_create_partition_table";
    String comment = "test comment";
    String key = "test key";
    String value = "test value";

    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id STRING COMMENT 'USER_ID', "
                      + " order_amount DOUBLE COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT '%s'"
                      + " PARTITIONED BY (user_id, order_amount)"
                      + " WITH ("
                      + "'%s' = '%s')",
                  tableName, comment, key, value);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          Table table =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName));
          Assertions.assertNotNull(table);
          Assertions.assertEquals(comment, table.comment());
          Assertions.assertEquals(value, table.properties().get(key));
          Column[] columns =
              new Column[] {
                Column.of("user_id", Types.StringType.get(), "USER_ID"),
                Column.of("order_amount", Types.DoubleType.get(), "ORDER_AMOUNT")
              };
          assertColumns(columns, table.columns());
          Transform[] partitions =
              new Transform[] {Transforms.identity("user_id"), Transforms.identity("order_amount")};
          Assertions.assertArrayEquals(partitions, table.partitioning());
        });
  }

  @Test
  public void testRenameColumn() {
    String tableName = "test_rename_column";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          result = sql("ALTER TABLE %s RENAME user_id TO user_id_new", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          Column[] actual =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .columns();
          Column[] expected =
              new Column[] {
                Column.of("user_id_new", Types.IntegerType.get(), "USER_ID"),
                Column.of("order_amount", Types.FloatType.get(), "ORDER_AMOUNT")
              };
          assertColumns(expected, actual);
        });
  }

  @Test
  public void testAlterTableAddColumn() {
    String tableName = "test_alter_table_add_column";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result = sql("ALTER TABLE %s ADD new_column_2 INT AFTER order_amount", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          Column[] actual =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .columns();
          Column[] expected =
              new Column[] {
                Column.of("user_id", Types.IntegerType.get(), "USER_ID"),
                Column.of("order_amount", Types.FloatType.get(), "ORDER_AMOUNT"),
                Column.of("new_column_2", Types.IntegerType.get(), null),
              };
          assertColumns(expected, actual);
        });
  }

  @Test
  public void testAlterTableDropColumn() {
    String tableName = "test_alter_table_drop_column";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result = sql("ALTER TABLE %s DROP user_id", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Column[] actual =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .columns();
          Column[] expected =
              new Column[] {Column.of("order_amount", Types.FloatType.get(), "ORDER_AMOUNT")};
          assertColumns(expected, actual);
        });
  }

  @Test
  public void testAlterTableAlterColumn() {
    String tableName = "test_alter_table_rename_column";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id DOUBLE COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'"
                      + " WITH ("
                      + "'%s' = '%s')",
                  tableName, "test key", "test value");
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result =
              sql("ALTER TABLE %s MODIFY order_amount DOUBLE COMMENT 'new comment2'", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result =
              sql(
                  "ALTER TABLE %s MODIFY user_id DOUBLE COMMENT 'new comment' AFTER order_amount",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Column[] actual =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .columns();
          Column[] expected =
              new Column[] {
                Column.of("order_amount", Types.DoubleType.get(), "new comment2"),
                Column.of("user_id", Types.DoubleType.get(), "new comment")
              };
          assertColumns(expected, actual);
        });
  }

  @Test
  public void testRenameTableAndModifyComment() {
    String tableName = "test_rename_table";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          String newTableName = "new_rename_table_name";
          result = sql("ALTER TABLE %s RENAME TO %s", tableName, newTableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Assertions.assertFalse(
              catalog
                  .asTableCatalog()
                  .tableExists(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName)));
          Assertions.assertTrue(
              catalog
                  .asTableCatalog()
                  .tableExists(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, newTableName)));
        });
  }

  @Test
  public void testAlterTableProperties() {
    String tableName = "test_alter_table_properties";
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          TableResult result =
              sql(
                  "CREATE TABLE %s "
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'"
                      + " WITH ("
                      + "'%s' = '%s')",
                  tableName, "key", "value");
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result = sql("ALTER TABLE %s SET ('key2' = 'value2', 'key' = 'value1')", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Map<String, String> properties =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .properties();
          Assertions.assertEquals(ImmutableMap.of("key2", "value2", "key", "value1"), properties);
          result = sql("ALTER TABLE %s UNSET ('key2')", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);

          properties =
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName))
                  .properties();
          Assertions.assertEquals(ImmutableMap.of("key", "value1"), properties);
        });
  }

  @Test
  public void testListTables() {
    Catalog currentCatalog = metalake.loadCatalog(defaultHiveCatalog);
    String newSchema = "test_list_table_catalog";
    try {
      Column[] columns = new Column[] {Column.of("user_id", Types.IntegerType.get(), "USER_ID")};
      doWithSchema(
          currentCatalog,
          newSchema,
          catalog -> {
            catalog
                .asTableCatalog()
                .createTable(
                    NameIdentifier.of(
                        metalake.name(), defaultHiveCatalog, newSchema, "test_table1"),
                    columns,
                    "comment1",
                    ImmutableMap.of());
            catalog
                .asTableCatalog()
                .createTable(
                    NameIdentifier.of(
                        metalake.name(), defaultHiveCatalog, newSchema, "test_table2"),
                    columns,
                    "comment2",
                    ImmutableMap.of());
            TableResult result = sql("SHOW TABLES");
            TestUtils.assertTableResult(
                result,
                ResultKind.SUCCESS_WITH_CONTENT,
                Row.of("test_table1"),
                Row.of("test_table2"));
          });
    } finally {
      currentCatalog.asSchemas().dropSchema(newSchema, true);
    }
  }

  @Test
  public void testDropTable() {
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          String tableName = "test_drop_table";
          Column[] columns =
              new Column[] {Column.of("user_id", Types.IntegerType.get(), "USER_ID")};
          NameIdentifier identifier =
              NameIdentifier.of(metalake.name(), defaultHiveCatalog, defaultHiveSchema, tableName);
          catalog.asTableCatalog().createTable(identifier, columns, "comment1", ImmutableMap.of());
          Assertions.assertTrue(catalog.asTableCatalog().tableExists(identifier));

          TableResult result = sql("DROP TABLE %s", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Assertions.assertFalse(catalog.asTableCatalog().tableExists(identifier));
        });
  }

  @Test
  public void testGetTable() {
    Column[] columns =
        new Column[] {
          Column.of("string_type", Types.StringType.get(), "string_type", true, false, null),
          Column.of("double_type", Types.DoubleType.get(), "double_type"),
          Column.of("int_type", Types.IntegerType.get(), "int_type"),
          Column.of("varchar_type", Types.StringType.get(), "varchar_type"),
          Column.of("char_type", Types.FixedCharType.of(1), "char_type"),
          Column.of("boolean_type", Types.BooleanType.get(), "boolean_type"),
          Column.of("byte_type", Types.ByteType.get(), "byte_type"),
          Column.of("binary_type", Types.BinaryType.get(), "binary_type"),
          Column.of("decimal_type", Types.DecimalType.of(10, 2), "decimal_type"),
          Column.of("bigint_type", Types.LongType.get(), "bigint_type"),
          Column.of("float_type", Types.FloatType.get(), "float_type"),
          Column.of("date_type", Types.DateType.get(), "date_type"),
          Column.of("timestamp_type", Types.TimestampType.withoutTimeZone(), "timestamp_type"),
          Column.of("smallint_type", Types.ShortType.get(), "smallint_type"),
          Column.of("array_type", Types.ListType.of(Types.IntegerType.get(), true), "array_type"),
          Column.of(
              "map_type",
              Types.MapType.of(Types.IntegerType.get(), Types.StringType.get(), true),
              "map_type"),
          Column.of(
              "struct_type",
              Types.StructType.of(
                  Types.StructType.Field.nullableField("k1", Types.IntegerType.get()),
                  Types.StructType.Field.nullableField("k2", Types.StringType.get())),
              null)
        };

    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          String tableName = "test_desc_table";
          String comment = "comment1";
          catalog
              .asTableCatalog()
              .createTable(
                  NameIdentifier.of(
                      metalake.name(), defaultHiveCatalog, defaultHiveSchema, "test_desc_table"),
                  columns,
                  comment,
                  ImmutableMap.of("k1", "v1"));

          Optional<org.apache.flink.table.catalog.Catalog> flinkCatalog =
              tableEnv.getCatalog(defaultHiveCatalog);
          Assertions.assertTrue(flinkCatalog.isPresent());
          try {
            CatalogBaseTable table =
                flinkCatalog.get().getTable(new ObjectPath(defaultHiveSchema, tableName));
            Assertions.assertNotNull(table);
            Assertions.assertEquals(CatalogBaseTable.TableKind.TABLE, table.getTableKind());
            Assertions.assertEquals(comment, table.getComment());
            Assertions.assertInstanceOf(ResolvedCatalogBaseTable.class, table);
            CatalogTable catalogTable = (CatalogTable) table;
            Assertions.assertFalse(catalogTable.isPartitioned());

            org.apache.flink.table.catalog.Column[] expected =
                new org.apache.flink.table.catalog.Column[] {
                  org.apache.flink.table.catalog.Column.physical("string_type", DataTypes.STRING())
                      .withComment("string_type"),
                  org.apache.flink.table.catalog.Column.physical("double_type", DataTypes.DOUBLE())
                      .withComment("double_type"),
                  org.apache.flink.table.catalog.Column.physical("int_type", DataTypes.INT())
                      .withComment("int_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "varchar_type", DataTypes.VARCHAR(Integer.MAX_VALUE))
                      .withComment("varchar_type"),
                  org.apache.flink.table.catalog.Column.physical("char_type", DataTypes.CHAR(1))
                      .withComment("char_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "boolean_type", DataTypes.BOOLEAN())
                      .withComment("boolean_type"),
                  org.apache.flink.table.catalog.Column.physical("byte_type", DataTypes.TINYINT())
                      .withComment("byte_type"),
                  org.apache.flink.table.catalog.Column.physical("binary_type", DataTypes.BYTES())
                      .withComment("binary_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "decimal_type", DataTypes.DECIMAL(10, 2))
                      .withComment("decimal_type"),
                  org.apache.flink.table.catalog.Column.physical("bigint_type", DataTypes.BIGINT())
                      .withComment("bigint_type"),
                  org.apache.flink.table.catalog.Column.physical("float_type", DataTypes.FLOAT())
                      .withComment("float_type"),
                  org.apache.flink.table.catalog.Column.physical("date_type", DataTypes.DATE())
                      .withComment("date_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "timestamp_type", DataTypes.TIMESTAMP(3))
                      .withComment("timestamp_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "smallint_type", DataTypes.SMALLINT())
                      .withComment("smallint_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "array_type", DataTypes.ARRAY(DataTypes.INT()))
                      .withComment("array_type"),
                  org.apache.flink.table.catalog.Column.physical(
                          "map_type", DataTypes.MAP(DataTypes.INT(), DataTypes.STRING()))
                      .withComment("map_type"),
                  org.apache.flink.table.catalog.Column.physical(
                      "struct_type",
                      DataTypes.ROW(
                          DataTypes.FIELD("k1", DataTypes.INT()),
                          DataTypes.FIELD("k2", DataTypes.STRING())))
                };
            ResolvedCatalogBaseTable<?> resolvedTable = (ResolvedCatalogBaseTable<?>) table;
            Assertions.assertArrayEquals(
                expected, resolvedTable.getResolvedSchema().getColumns().toArray());
          } catch (TableNotExistException e) {
            Assertions.fail(e);
          }
        });
  }
}
