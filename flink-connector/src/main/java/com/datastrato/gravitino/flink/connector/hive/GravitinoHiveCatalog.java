/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.flink.connector.hive;

import com.datastrato.gravitino.flink.connector.catalog.BaseCatalog;
import java.util.Optional;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.apache.flink.table.factories.Factory;

public class GravitinoHiveCatalog extends BaseCatalog {
  private GravitinoHiveCatalog(String catalogName, String defaultDatabase, String metalakeName, String metalakeUrl) {
    super(catalogName, defaultDatabase, metalakeName, metalakeUrl);
  }

  public GravitinoHiveCatalog(HiveCatalog catalog, String metalakeName, String metalakeUri) {
    this(catalog.getName(), catalog.getDefaultDatabase(), metalakeName, metalakeUri);
  }

  @Override
  public Optional<Factory> getFactory() {
    return Optional.empty();
  }
}
