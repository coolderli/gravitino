/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.integration.test.hive;

import com.datastrato.gravitino.flink.connector.integration.test.FlinkEnvIT;
import com.google.common.collect.ImmutableMap;
import jline.internal.Preconditions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class FlinkHiveCatalogBaseIT extends FlinkEnvIT {
  protected static final String DEFAULT_CATALOG = "default_catalog";
  protected static final String defaultHiveCatalog = "default_flink_hive_catalog";
  protected static final String defaultHiveSchema = "default_flink_hive_schema";

  @BeforeAll
  static void startHiveBase() {
    initDefaultHiveCatalog();
  }

  @AfterAll
  static void stopHiveBase() {}

  protected static void initDefaultHiveCatalog() {
    Preconditions.checkNotNull(metalake);
    metalake.createCatalog(
        defaultHiveCatalog,
        com.datastrato.gravitino.Catalog.Type.RELATIONAL,
        "hive",
        null,
        ImmutableMap.of("metastore.uris", hiveMetastoreUri));
  }

  protected static void initDefaultHiveSchema() {
    Preconditions.checkNotNull(metalake);
    com.datastrato.gravitino.Catalog catalog = metalake.loadCatalog(defaultHiveCatalog);
    Preconditions.checkNotNull(catalog);
    if (!catalog.asSchemas().schemaExists(defaultHiveSchema)) {
      catalog.asSchemas().createSchema(defaultHiveSchema, null, ImmutableMap.of());
    }
  }
}
