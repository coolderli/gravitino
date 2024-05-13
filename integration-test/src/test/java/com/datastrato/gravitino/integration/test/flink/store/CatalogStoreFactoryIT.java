/*
 *  Copyright 2024 Datastrato Pvt Ltd.
 *  This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.integration.test.flink.store;

import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.flink.connector.hive.GravitinoHiveCatalog;
import com.datastrato.gravitino.flink.connector.hive.GravitinoHiveCatalogFactory;
import com.datastrato.gravitino.integration.test.flink.FlinkEnvIT;
import java.util.Arrays;
import java.util.Optional;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.flink.table.catalog.hive.factories.HiveCatalogFactoryOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CatalogStoreFactoryIT extends FlinkEnvIT {

  private static final String DEFAULT_CATALOG = "default_catalog";

  @Test
  public void testCreateGravitinoHiveCatalog() {
    String catalogName = "gravitino-hive";
    Configuration configuration = new Configuration();
    configuration.set(CommonCatalogOptions.CATALOG_TYPE, GravitinoHiveCatalogFactory.IDENTIFIER);
    configuration.set(HiveCatalogFactoryOptions.HIVE_CONF_DIR, "src/test/resources/flink-tests");
    CatalogDescriptor catalogDescriptor = CatalogDescriptor.of(catalogName, configuration);
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.createCatalog(catalogName, catalogDescriptor);
    Assertions.assertTrue(
        metalake.catalogExists(NameIdentifier.ofCatalog(metalakeName, catalogName)));

    Optional<org.apache.flink.table.catalog.Catalog> catalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(catalog.isPresent());
    Assertions.assertInstanceOf(GravitinoHiveCatalog.class, catalog.get());

    String[] catalogs = tableEnv.listCatalogs();
    Assertions.assertTrue(catalogs.length > 1, "Should create a new catalog");
    Assertions.assertTrue(
        Arrays.asList(catalogs).contains(catalogName), "Should create the correct catalog.");

    Assertions.assertEquals(
        DEFAULT_CATALOG,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be default_catalog in flink");
    tableEnv.useCatalog(catalogName);
    Assertions.assertEquals(
        catalogName,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be the one that is created just now.");
  }

  @Test
  public void testCreateGravitinoHiveCatalogUsingSQL() {
    String catalogName = "gravitino_hive_sql";
    Configuration configuration = new Configuration();
    configuration.set(CommonCatalogOptions.CATALOG_TYPE, GravitinoHiveCatalogFactory.IDENTIFIER);
    configuration.set(HiveCatalogFactoryOptions.HIVE_CONF_DIR, "src/test/resources/flink-tests");
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.executeSql(
        String.format(
            "create catalog %s with ('type'='gravitino-hive', "
                + "'hive-conf-dir'='src/test/resources/flink-tests')",
            catalogName));
    Assertions.assertTrue(
        metalake.catalogExists(NameIdentifier.ofCatalog(metalakeName, catalogName)));

    Optional<org.apache.flink.table.catalog.Catalog> catalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(catalog.isPresent());
    Assertions.assertInstanceOf(GravitinoHiveCatalog.class, catalog.get());

    String[] catalogs = tableEnv.listCatalogs();
    Assertions.assertTrue(catalogs.length > 1, "Should create a new catalog");
    Assertions.assertTrue(
        Arrays.asList(catalogs).contains(catalogName), "Should create the correct catalog.");

    Assertions.assertEquals(
        DEFAULT_CATALOG,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be default_catalog in flink");
    tableEnv.useCatalog(catalogName);
    Assertions.assertEquals(
        catalogName,
        tableEnv.getCurrentCatalog(),
        "Current catalog should be the one that is created just now.");
  }
}
