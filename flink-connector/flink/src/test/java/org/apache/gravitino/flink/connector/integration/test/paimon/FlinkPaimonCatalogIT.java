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
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.flink.table.catalog.hive.factories.HiveCatalogFactoryOptions;
import org.apache.gravitino.Catalog;
import org.apache.gravitino.flink.connector.hive.GravitinoHiveCatalogFactoryOptions;
import org.apache.gravitino.flink.connector.integration.test.FlinkCommonIT;
import org.apache.gravitino.flink.connector.paimon.GravitinoPaimonCatalogFactoryOptions;
import org.apache.paimon.options.CatalogOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
            "paimon",
            null,
            ImmutableMap.of("uri", hiveMetastoreUri));
  }

  @Test
  public void testCreateGravitinoPaimonTable() {
    tableEnv.useCatalog(DEFAULT_CATALOG);
    int nums = metalake.listCatalogs().length;

    // create a new paimon catalog
    String catalogName = "gravitino_paimon";
    Configuration configuration = new Configuration();
    configuration.set(
            CommonCatalogOptions.CATALOG_TYPE, GravitinoPaimonCatalogFactoryOptions.IDENTIFIER);
    configuration.set(CatalogOptions.METASTORE.key(), hiveMetastoreUri);

    CatalogDescriptor catalogDescriptor = CatalogDescriptor.of(catalogName, configuration);
    tableEnv.createCatalog(catalogName, catalogDescriptor);
    Assertions.assertTrue(metalake.catalogExists(catalogName));
  }
}
