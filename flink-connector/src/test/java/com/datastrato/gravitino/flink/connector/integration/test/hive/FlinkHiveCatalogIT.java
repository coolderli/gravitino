/*
 *  Copyright 2024 Datastrato Pvt Ltd.
 *  This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.flink.connector.integration.test.hive;

import static com.datastrato.gravitino.catalog.hive.HiveCatalogPropertiesMeta.METASTORE_URIS;
import static com.datastrato.gravitino.rel.expressions.transforms.Transforms.EMPTY_TRANSFORM;

import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.Schema;
import com.datastrato.gravitino.catalog.hive.HiveSchemaPropertiesMetadata;
import com.datastrato.gravitino.flink.connector.PropertiesConverter;
import com.datastrato.gravitino.flink.connector.hive.GravitinoHiveCatalog;
import com.datastrato.gravitino.flink.connector.hive.GravitinoHiveCatalogFactoryOptions;
import com.datastrato.gravitino.flink.connector.integration.test.FlinkEnvIT;
import com.datastrato.gravitino.flink.connector.integration.test.utils.TestUtils;
import com.datastrato.gravitino.rel.Column;
import com.datastrato.gravitino.rel.Table;
import com.datastrato.gravitino.rel.expressions.transforms.Transform;
import com.datastrato.gravitino.rel.expressions.transforms.Transforms;
import com.datastrato.gravitino.rel.types.Types;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jline.internal.Preconditions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.ResultKind;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.flink.table.catalog.hive.factories.HiveCatalogFactoryOptions;
import org.apache.flink.types.Row;
import org.apache.hadoop.hive.conf.HiveConf;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FlinkHiveCatalogIT extends FlinkEnvIT {

  private static final String DEFAULT_CATALOG = "default_catalog";
  private static final String defaultHiveCatalog = "default_flink_hive_catalog";
  private static final String defaultHiveSchema = "default_flink_hive_schema";

  @BeforeAll
  static void startup() {
    initDefaultHiveCatalog();
    initDefaultHiveSchema();
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
  }

  @Test
  public void testCreateGravitinoHiveCatalog() {
    tableEnv.useCatalog(DEFAULT_CATALOG);

    // Create a new catalog.
    String catalogName = "gravitino_hive";
    Configuration configuration = new Configuration();
    configuration.set(
        CommonCatalogOptions.CATALOG_TYPE, GravitinoHiveCatalogFactoryOptions.IDENTIFIER);
    configuration.set(HiveCatalogFactoryOptions.HIVE_CONF_DIR, "src/test/resources/flink-tests");
    configuration.set(
        GravitinoHiveCatalogFactoryOptions.HIVE_METASTORE_URIS, "thrift://127.0.0.1:9084");
    CatalogDescriptor catalogDescriptor = CatalogDescriptor.of(catalogName, configuration);
    tableEnv.createCatalog(catalogName, catalogDescriptor);
    Assertions.assertTrue(metalake.catalogExists(catalogName));

    // Check the catalog properties.
    com.datastrato.gravitino.Catalog gravitinoCatalog = metalake.loadCatalog(catalogName);
    Map<String, String> properties = gravitinoCatalog.properties();
    Assertions.assertEquals("thrift://127.0.0.1:9084", properties.get(METASTORE_URIS));
    Map<String, String> flinkProperties =
        gravitinoCatalog.properties().entrySet().stream()
            .filter(e -> e.getKey().startsWith(PropertiesConverter.FLINK_PROPERTY_PREFIX))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Assertions.assertEquals(2, flinkProperties.size());
    Assertions.assertEquals(
        "src/test/resources/flink-tests",
        flinkProperties.get(flinkByPass(HiveCatalogFactoryOptions.HIVE_CONF_DIR.key())));
    Assertions.assertEquals(
        GravitinoHiveCatalogFactoryOptions.IDENTIFIER,
        flinkProperties.get(flinkByPass(CommonCatalogOptions.CATALOG_TYPE.key())));

    // Get the created catalog.
    Optional<org.apache.flink.table.catalog.Catalog> catalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(catalog.isPresent());
    Assertions.assertInstanceOf(GravitinoHiveCatalog.class, catalog.get());

    // List catalogs.
    String[] catalogs = tableEnv.listCatalogs();
    Assertions.assertEquals(3, catalogs.length, "Should create a new catalog");
    Assertions.assertTrue(
        Arrays.asList(catalogs).contains(catalogName), "Should create the correct catalog.");

    Assertions.assertEquals(
        DEFAULT_CATALOG,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be default_catalog in flink");

    // Change the current catalog to the new created catalog.
    tableEnv.useCatalog(catalogName);
    Assertions.assertEquals(
        catalogName,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be the one that is created just now.");

    // Drop the catalog. Only support drop catalog by SQL.
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.executeSql("drop catalog " + catalogName);
    Assertions.assertFalse(metalake.catalogExists(catalogName));

    Optional<Catalog> droppedCatalog = tableEnv.getCatalog(catalogName);
    Assertions.assertFalse(droppedCatalog.isPresent(), "Catalog should be dropped");
  }

  @Test
  public void testCreateGravitinoHiveCatalogUsingSQL() {
    tableEnv.useCatalog(DEFAULT_CATALOG);

    // Create a new catalog.
    String catalogName = "gravitino_hive_sql";
    tableEnv.executeSql(
        String.format(
            "create catalog %s with ("
                + "'type'='gravitino-hive', "
                + "'hive-conf-dir'='src/test/resources/flink-tests',"
                + "'hive.metastore.uris'='thrift://127.0.0.1:9084',"
                + "'unknown.key'='unknown.value'"
                + ")",
            catalogName));
    Assertions.assertTrue(metalake.catalogExists(catalogName));

    // Check the properties of the created catalog.
    com.datastrato.gravitino.Catalog gravitinoCatalog = metalake.loadCatalog(catalogName);
    Map<String, String> properties = gravitinoCatalog.properties();
    Assertions.assertEquals("thrift://127.0.0.1:9084", properties.get(METASTORE_URIS));
    Map<String, String> flinkProperties =
        properties.entrySet().stream()
            .filter(e -> e.getKey().startsWith(PropertiesConverter.FLINK_PROPERTY_PREFIX))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Assertions.assertEquals(3, flinkProperties.size());
    Assertions.assertEquals(
        "src/test/resources/flink-tests",
        flinkProperties.get(flinkByPass(HiveCatalogFactoryOptions.HIVE_CONF_DIR.key())));
    Assertions.assertEquals(
        GravitinoHiveCatalogFactoryOptions.IDENTIFIER,
        flinkProperties.get(flinkByPass(CommonCatalogOptions.CATALOG_TYPE.key())));
    Assertions.assertEquals(
        "unknown.value",
        flinkProperties.get(flinkByPass("unknown.key")),
        "The unknown.key will not cause failure and will be saved in Gravitino.");

    // Get the created catalog.
    Optional<org.apache.flink.table.catalog.Catalog> catalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(catalog.isPresent());
    Assertions.assertInstanceOf(GravitinoHiveCatalog.class, catalog.get());

    // List catalogs.
    String[] catalogs = tableEnv.listCatalogs();
    Assertions.assertEquals(3, catalogs.length, "Should create a new catalog");
    Assertions.assertTrue(
        Arrays.asList(catalogs).contains(catalogName), "Should create the correct catalog.");

    // Use SQL to list catalogs.
    TableResult result = tableEnv.executeSql("show catalogs");
    Assertions.assertEquals(
        3, Lists.newArrayList(result.collect()).size(), "Should have 2 catalogs");

    Assertions.assertEquals(
        DEFAULT_CATALOG,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be default_catalog in flink");

    // Change the current catalog to the new created catalog.
    tableEnv.useCatalog(catalogName);
    Assertions.assertEquals(
        catalogName,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be the one that is created just now.");

    // Drop the catalog. Only support using SQL to drop catalog.
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.executeSql("drop catalog " + catalogName);
    Assertions.assertFalse(metalake.catalogExists(catalogName));

    Optional<Catalog> droppedCatalog = tableEnv.getCatalog(catalogName);
    Assertions.assertFalse(droppedCatalog.isPresent(), "Catalog should be dropped");
  }

  @Test
  public void testCreateGravitinoHiveCatalogRequireOptions() {
    tableEnv.useCatalog(DEFAULT_CATALOG);

    // Failed to create the catalog for missing the required options.
    String catalogName = "gravitino_hive_sql2";
    Assertions.assertThrows(
        ValidationException.class,
        () -> {
          tableEnv.executeSql(
              String.format(
                  "create catalog %s with ("
                      + "'type'='gravitino-hive', "
                      + "'hive-conf-dir'='src/test/resources/flink-tests'"
                      + ")",
                  catalogName));
        },
        "The hive.metastore.uris is required.");

    Assertions.assertFalse(metalake.catalogExists(catalogName));
  }

  @Test
  public void testGetCatalogFromGravitino() {
    // list catalogs.
    String[] catalogs = tableEnv.listCatalogs();
    int size = catalogs.length;
    Assertions.assertTrue(catalogs.length > 1, "Only have 2 catalog");

    // create a new catalog.
    String catalogName = "hive_catalog_in_gravitino";
    com.datastrato.gravitino.Catalog gravitinoCatalog =
        metalake.createCatalog(
            catalogName,
            com.datastrato.gravitino.Catalog.Type.RELATIONAL,
            "hive",
            null,
            ImmutableMap.of(
                "flink.bypass.hive-conf-dir",
                "src/test/resources/flink-tests",
                "flink.bypass.hive.test",
                "hive.config",
                "metastore.uris",
                "thrift://127.0.0.1:9084"));
    Assertions.assertNotNull(gravitinoCatalog);
    Assertions.assertEquals(catalogName, gravitinoCatalog.name());
    Assertions.assertTrue(metalake.catalogExists(catalogName));
    Assertions.assertEquals(
        size + 1, tableEnv.listCatalogs().length, "Should create a new catalog");

    // get the catalog from gravitino.
    Optional<Catalog> flinkHiveCatalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(flinkHiveCatalog.isPresent());
    Assertions.assertInstanceOf(GravitinoHiveCatalog.class, flinkHiveCatalog.get());
    GravitinoHiveCatalog gravitinoHiveCatalog = (GravitinoHiveCatalog) flinkHiveCatalog.get();
    HiveConf hiveConf = gravitinoHiveCatalog.getHiveConf();
    Assertions.assertTrue(hiveConf.size() > 0, "Should have hive conf");
    Assertions.assertEquals("hive.config", hiveConf.get("hive.test"));
    Assertions.assertEquals(
        "thrift://127.0.0.1:9084", hiveConf.get(HiveConf.ConfVars.METASTOREURIS.varname));

    // drop the catalog.
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.executeSql("drop catalog " + catalogName);
    Assertions.assertFalse(metalake.catalogExists(catalogName));
    Assertions.assertEquals(
        size, tableEnv.listCatalogs().length, "The created catalog should be dropped.");
  }

  @Test
  public void testCreateSchema() {
    doWithCatalog(
        metalake.loadCatalog(defaultHiveCatalog),
        catalog -> {
          String schema = "test_create_schema";
          try {
            TableResult tableResult =
                tableEnv.executeSql(String.format("CREATE DATABASE IF NOT EXISTS %s", schema));
            TestUtils.assertTableResult(tableResult, ResultKind.SUCCESS);
            catalog.asSchemas().schemaExists(schema);
          } finally {
            catalog.asSchemas().dropSchema(schema, true);
            Assertions.assertFalse(catalog.asSchemas().schemaExists(schema));
          }
        });
  }

  @Test
  public void testGetSchema() {
    doWithCatalog(
        metalake.loadCatalog(defaultHiveCatalog),
        catalog -> {
          String schema = "test_get_schema";
          String comment = "test comment";
          String propertyKey = "key1";
          String propertyValue = "value1";
          String location = warehouse + "/" + schema;

          try {
            TestUtils.assertTableResult(
                tableEnv.executeSql(
                    String.format(
                        "CREATE DATABASE IF NOT EXISTS %s COMMENT '%s' WITH ('%s'='%s', '%s'='%s')",
                        schema, comment, propertyKey, propertyValue, "location", location)),
                ResultKind.SUCCESS);
            TestUtils.assertTableResult(tableEnv.executeSql("USE " + schema), ResultKind.SUCCESS);

            catalog.asSchemas().schemaExists(schema);
            Schema loadedSchema = catalog.asSchemas().loadSchema(schema);
            Assertions.assertEquals(schema, loadedSchema.name());
            Assertions.assertEquals(comment, loadedSchema.comment());
            Assertions.assertEquals(2, loadedSchema.properties().size());
            Assertions.assertEquals(propertyValue, loadedSchema.properties().get(propertyKey));
            Assertions.assertEquals(
                location, loadedSchema.properties().get(HiveSchemaPropertiesMetadata.LOCATION));
          } finally {
            catalog.asSchemas().dropSchema(schema, true);
            Assertions.assertFalse(catalog.asSchemas().schemaExists(schema));
          }
        });
  }

  @Test
  public void testListSchema() {
    String newCatalogName = "test_list_schema";
    com.datastrato.gravitino.Catalog newCatalog =
        metalake.createCatalog(
            newCatalogName,
            com.datastrato.gravitino.Catalog.Type.RELATIONAL,
            "hive",
            null,
            ImmutableMap.of("metastore.uris", hiveMetastoreUri));

    try {
      doWithCatalog(
          newCatalog,
          catalog -> {
            Assertions.assertEquals(1, catalog.asSchemas().listSchemas().length);
            String schema = "test_list_schema";
            String schema2 = "test_list_schema2";
            String schema3 = "test_list_schema3";

            try {
              TestUtils.assertTableResult(
                  tableEnv.executeSql(String.format("CREATE DATABASE IF NOT EXISTS %s", schema)),
                  ResultKind.SUCCESS);

              TestUtils.assertTableResult(
                  tableEnv.executeSql(String.format("CREATE DATABASE IF NOT EXISTS %s", schema2)),
                  ResultKind.SUCCESS);

              TestUtils.assertTableResult(
                  tableEnv.executeSql(String.format("CREATE DATABASE IF NOT EXISTS %s", schema3)),
                  ResultKind.SUCCESS);
              TestUtils.assertTableResult(
                  tableEnv.executeSql("SHOW DATABASES"),
                  ResultKind.SUCCESS_WITH_CONTENT,
                  Row.of("default"),
                  Row.of(schema),
                  Row.of(schema2),
                  Row.of(schema3));

              String[] schemas = catalog.asSchemas().listSchemas();
              Assertions.assertEquals(4, schemas.length);
              Assertions.assertEquals("default", schemas[0]);
              Assertions.assertEquals(schema, schemas[1]);
              Assertions.assertEquals(schema2, schemas[2]);
              Assertions.assertEquals(schema3, schemas[3]);
            } finally {
              catalog.asSchemas().dropSchema(schema, true);
              catalog.asSchemas().dropSchema(schema2, true);
              catalog.asSchemas().dropSchema(schema3, true);
              Assertions.assertEquals(1, catalog.asSchemas().listSchemas().length);
            }
          });
    } finally {
      metalake.dropCatalog(newCatalogName);
    }
  }

  @Test
  public void testAlterSchema() {
    doWithCatalog(
        metalake.loadCatalog(defaultHiveCatalog),
        catalog -> {
          String schema = "test_alter_schema";
          try {
            TestUtils.assertTableResult(
                tableEnv.executeSql(
                    String.format(
                        "CREATE DATABASE IF NOT EXISTS %s "
                            + "COMMENT 'test comment'"
                            + "WITH ('key1' = 'value1', 'key2'='value2')",
                        schema)),
                ResultKind.SUCCESS);

            Schema loadedSchema = catalog.asSchemas().loadSchema(schema);
            Assertions.assertEquals(schema, loadedSchema.name());
            Assertions.assertEquals("test comment", loadedSchema.comment());
            Assertions.assertEquals(3, loadedSchema.properties().size());
            Assertions.assertEquals("value1", loadedSchema.properties().get("key1"));
            Assertions.assertEquals("value2", loadedSchema.properties().get("key2"));
            Assertions.assertNotNull(loadedSchema.properties().get("location"));

            TestUtils.assertTableResult(
                tableEnv.executeSql(
                    String.format(
                        "ALTER DATABASE %s SET ('key1'='new-value', 'key3'='value3')", schema)),
                ResultKind.SUCCESS);
            Schema reloadedSchema = catalog.asSchemas().loadSchema(schema);
            Assertions.assertEquals(schema, reloadedSchema.name());
            Assertions.assertEquals("test comment", reloadedSchema.comment());
            Assertions.assertEquals(4, reloadedSchema.properties().size());
            Assertions.assertEquals("new-value", reloadedSchema.properties().get("key1"));
            Assertions.assertEquals("value3", reloadedSchema.properties().get("key3"));
          } finally {
            catalog.asSchemas().dropSchema(schema, true);
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

  private static void initDefaultHiveCatalog() {
    Preconditions.checkNotNull(metalake);
    metalake.createCatalog(
        defaultHiveCatalog,
        com.datastrato.gravitino.Catalog.Type.RELATIONAL,
        "hive",
        null,
        ImmutableMap.of("metastore.uris", hiveMetastoreUri));
  }

  private static void initDefaultHiveSchema() {
    Preconditions.checkNotNull(metalake);
    com.datastrato.gravitino.Catalog catalog = metalake.loadCatalog(defaultHiveCatalog);
    Preconditions.checkNotNull(catalog);
    catalog.asSchemas().createSchema(defaultHiveSchema, null, ImmutableMap.of());
  }
}
