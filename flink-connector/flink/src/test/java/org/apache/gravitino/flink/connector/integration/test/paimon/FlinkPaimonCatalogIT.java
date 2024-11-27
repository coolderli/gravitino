/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.gravitino.flink.connector.integration.test.paimon;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.ResultKind;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.gravitino.Catalog;
import org.apache.gravitino.catalog.lakehouse.paimon.PaimonCatalogPropertiesMetadata;
import org.apache.gravitino.exceptions.NoSuchCatalogException;
import org.apache.gravitino.flink.connector.integration.test.FlinkCommonIT;
import org.apache.gravitino.flink.connector.integration.test.utils.TestUtils;
import org.apache.gravitino.flink.connector.paimon.GravitinoPaimonCatalog;
import org.apache.gravitino.flink.connector.paimon.GravitinoPaimonCatalogFactoryOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

@Tag("gravitino-docker-test")
public class FlinkPaimonCatalogIT extends FlinkCommonIT {
  private static final String DEFAULT_PAIMON_CATALOG = "test_flink_paimon_schema_catalog";

  private static org.apache.gravitino.Catalog paimonCatalog;

  @Override
  protected Catalog currentCatalog() {
    return paimonCatalog;
  }

  @BeforeAll
  static void paimonStartUp() {
    initDefaultPaimonCatalog();
  }

  @AfterAll
  static void paimonStop() {
    Preconditions.checkNotNull(metalake);
    metalake.dropCatalog(DEFAULT_PAIMON_CATALOG, true);
  }

  private static void initDefaultPaimonCatalog() {
    Preconditions.checkNotNull(metalake);
    paimonCatalog =
        metalake.createCatalog(
            DEFAULT_PAIMON_CATALOG,
            org.apache.gravitino.Catalog.Type.RELATIONAL,
            "lakehouse-paimon",
            null,
            ImmutableMap.of(
                "uri",
                hiveMetastoreUri,
                "warehouse",
                warehouse + "/default_paimon",
                "catalog-backend",
                "hive"));
  }

  @Test
  public void testCreateGravitinoPaimonTable() {
    tableEnv.useCatalog(DEFAULT_CATALOG);

    // create a new paimon catalog
    String catalogName = "gravitino_paimon";
    Configuration configuration = new Configuration();
    configuration.set(
        CommonCatalogOptions.CATALOG_TYPE, GravitinoPaimonCatalogFactoryOptions.IDENTIFIER);
    configuration.setString(
        wrapConfigOption(PaimonCatalogPropertiesMetadata.GRAVITINO_CATALOG_BACKEND), "hive");
    configuration.set(wrapConfigOption(PaimonCatalogPropertiesMetadata.URI), hiveMetastoreUri);
    configuration.set(
        wrapConfigOption(PaimonCatalogPropertiesMetadata.WAREHOUSE),
        warehouse + "/gravitino_paimon");
    CatalogDescriptor catalogDescriptor = CatalogDescriptor.of(catalogName, configuration);
    tableEnv.createCatalog(catalogName, catalogDescriptor);
    Assertions.assertTrue(metalake.catalogExists(catalogName));

    Catalog catalog = metalake.loadCatalog(catalogName);
    Assertions.assertEquals("lakehouse-paimon", catalog.provider());
    Assertions.assertEquals("gravitino_paimon", catalog.name());
    Assertions.assertEquals("hive", catalog.properties().get("catalog-backend"));
    Assertions.assertEquals(hiveMetastoreUri, catalog.properties().get("uri"));
    Assertions.assertEquals(warehouse + "/gravitino_paimon", catalog.properties().get("warehouse"));

    // test list catalogs
    String[] catalogs = metalake.listCatalogs();
    Assertions.assertTrue(Arrays.asList(catalogs).contains(catalogName));

    // change catalog
    tableEnv.useCatalog(catalogName);
    Assertions.assertEquals(catalogName, tableEnv.getCurrentCatalog());

    // test drop catalog
    tableEnv.useCatalog(DEFAULT_CATALOG);
    tableEnv.executeSql("drop catalog " + catalogName);
    Assertions.assertFalse(metalake.catalogExists(catalogName));
    Assertions.assertThrows(
        NoSuchCatalogException.class, () -> metalake.loadCatalog(catalogName));
  }

  @Test
  public void testCreatePaimonCatalogUsingSql() throws Exception {
    tableEnv.useCatalog(DEFAULT_CATALOG);

    String catalogName = "test_paimon_catalog_using_sql";
    Assertions.assertFalse(metalake.catalogExists(catalogName));
    String sql = String.format("create catalog %s "
            + " with ("
            + "'type'='gravitino-paimon',"
            + "'catalog-backend'='hive',"
            + "'uri'='%s',"
            + "'warehouse'='%s'"
            + ")", catalogName, hiveMetastoreUri, warehouse + "/test_paimon_catalog_using_sql");
    TableResult result = tableEnv.executeSql(sql);
    TestUtils.assertTableResult(result, ResultKind.SUCCESS);
    Assertions.assertTrue(metalake.catalogExists(catalogName));
  }

  @Test
  public void testLoadPaimonCatalogFromGravitino() {
    String catalogName = "test_paimon_catalog_from_gravitino";
    Catalog catalog = metalake.createCatalog(
            catalogName,
            Catalog.Type.RELATIONAL,
            "lakehouse-paimon",
            "test comment",
            ImmutableMap.of(
                    "uri",
                    hiveMetastoreUri,
                    "warehouse",
                    warehouse + "/test_paimon_catalog_from_gravitino",
                    "catalog-backend",
                    "hive")
    );
    Assertions.assertNotNull(catalog);
    Assertions.assertTrue(metalake.catalogExists(catalogName));

    Optional<org.apache.flink.table.catalog.Catalog> flinkCatalog = tableEnv.getCatalog(catalogName);
    Assertions.assertTrue(flinkCatalog.isPresent());
    Assertions.assertInstanceOf(GravitinoPaimonCatalog.class, flinkCatalog.get());
    GravitinoPaimonCatalog gravitinoPaimonCatalog = (GravitinoPaimonCatalog) flinkCatalog.get();
    Assertions.assertEquals(catalogName, gravitinoPaimonCatalog.getName());
  }
}
