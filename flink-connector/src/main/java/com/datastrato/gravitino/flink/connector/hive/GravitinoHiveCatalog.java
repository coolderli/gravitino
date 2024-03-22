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
  private GravitinoHiveCatalog(String catalogName, String defaultDatabase) {
    super(catalogName, defaultDatabase);
  }

  public GravitinoHiveCatalog(HiveCatalog catalog) {
    this(catalog.getName(), catalog.getDefaultDatabase());
  }

  @Override
  public Optional<Factory> getFactory() {
    return Optional.empty();
  }
}
