/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.integration.test.hive;

import static com.datastrato.gravitino.rel.expressions.transforms.Transforms.EMPTY_TRANSFORM;

import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.flink.connector.integration.test.FlinkEnvIT;
import com.datastrato.gravitino.flink.connector.integration.test.utils.TestUtils;
import com.datastrato.gravitino.rel.Column;
import com.datastrato.gravitino.rel.Table;
import com.datastrato.gravitino.rel.expressions.transforms.Transform;
import com.datastrato.gravitino.rel.expressions.transforms.Transforms;
import com.datastrato.gravitino.rel.types.Types;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.flink.table.api.ResultKind;
import org.apache.flink.table.api.TableResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkHiveTableOperationIT extends FlinkHiveCatalogBaseIT {
  private static final Logger LOG = LoggerFactory.getLogger(FlinkHiveTableOperationIT.class);

  @BeforeAll
  static void startup() {
    FlinkHiveCatalogBaseIT.startup();
    initDefaultHiveSchema();
    LOG.info("Create default schema!");
  }

  @AfterAll
  static void stop() {
    doWithSchema(
        metalake.loadCatalog(defaultHiveCatalog),
        defaultHiveSchema,
        catalog -> {
          if (catalog.asSchemas().schemaExists(defaultHiveSchema)) {
            catalog.asSchemas().dropSchema(defaultHiveSchema, true);
          }
        });
    FlinkHiveCatalogBaseIT.stop();
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
                      + "'connector'='hive',"
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
                      + "'connector'='hive',"
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

          result = sql("ALTER TABLE %s RENAME COLUMN user_id TO user_id_new", tableName);
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
          result = sql("ALTER TABLE %s ADD COLUMN new_column INT FIRST", tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result = sql("ALTER TABLE %s ADD COLUMN new_column_2 INT AFTER user_id", tableName);
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
                Column.of("new_column", Types.IntegerType.get(), null),
                Column.of("user_id", Types.IntegerType.get(), "USER_ID"),
                Column.of("new_column_2", Types.IntegerType.get(), null),
                Column.of("order_amount", Types.FloatType.get(), "ORDER_AMOUNT")
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
                      + "(user_id INT COMMENT 'USER_ID', "
                      + " order_amount FLOAT COMMENT 'ORDER_AMOUNT')"
                      + " COMMENT 'test comment'"
                      + " WITH ("
                      + "'connector'='hive',"
                      + "'%s' = '%s')",
                  tableName, "test key", "test value");
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result =
              sql(
                  "ALTER TABLE %s MODIFY order_amount TYPE DOUBLE COMMENT 'new comment2'",
                  tableName);
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          result =
              sql(
                  "ALTER TABLE %s MODIFY user_id TYPE BIGINT COMMENT 'new comment' AFTER order_amount",
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
                Column.of("user_id", Types.LongType.get(), "new comment")
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
          result = sql("ALTER TABLE %s RENAME TO %s", tableName, "new_table_name");
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
                          metalake.name(),
                          defaultHiveCatalog,
                          defaultHiveSchema,
                          "new_table_name")));

          result = sql("ALTER TABLE %s COMMENT 'new comment'", "new_table_name");
          TestUtils.assertTableResult(result, ResultKind.SUCCESS);
          Assertions.assertEquals(
              "new comment",
              catalog
                  .asTableCatalog()
                  .loadTable(
                      NameIdentifier.of(
                          metalake.name(), defaultHiveCatalog, defaultHiveSchema, "new_table_name"))
                  .comment());
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
                      + "'connector'='hive',"
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
  public void testListTables() {}

  @Test
  public void testDropTable() {}
}
