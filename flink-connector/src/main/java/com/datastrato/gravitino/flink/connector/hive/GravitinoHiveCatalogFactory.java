/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.hive;

import java.util.Set;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.apache.flink.table.catalog.hive.factories.HiveCatalogFactory;
import org.apache.flink.table.factories.CatalogFactory;

public class GravitinoHiveCatalogFactory implements CatalogFactory {
  public static final String IDENTIFIER = "gravitino-hive";

  private HiveCatalogFactory hiveCatalogFactory;

  @Override
  public Catalog createCatalog(Context context) {
    this.hiveCatalogFactory = new HiveCatalogFactory();
    HiveCatalog catalog = (HiveCatalog) this.hiveCatalogFactory.createCatalog(context);
    return new GravitinoHiveCatalog(catalog);
  }

  @Override
  public String factoryIdentifier() {
    return IDENTIFIER;
  }

  @Override
  public Set<ConfigOption<?>> requiredOptions() {
    return hiveCatalogFactory.requiredOptions();
  }

  @Override
  public Set<ConfigOption<?>> optionalOptions() {
    return hiveCatalogFactory.optionalOptions();
  }
}
