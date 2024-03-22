/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.store;

import com.datastrato.gravitino.Catalog;
import com.datastrato.gravitino.flink.connector.catalog.GravitinoCatalogManager;
import com.datastrato.gravitino.flink.connector.hive.GravitinoHiveCatalogFactory;
import java.util.Optional;
import java.util.Set;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.AbstractCatalogStore;
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.util.Preconditions;

public class GravitinoCatalogStore extends AbstractCatalogStore {

  private final GravitinoCatalogManager gravitinoCatalogManager;

  public GravitinoCatalogStore(GravitinoCatalogManager catalogManager) {
    this.gravitinoCatalogManager = catalogManager;
  }

  @Override
  public void storeCatalog(String catalogName, CatalogDescriptor descriptor)
      throws CatalogException {
    Configuration configuration = descriptor.getConfiguration();
    String provider = getCatalogProvider(configuration);
    Catalog.Type type = getGravitinoCatalogType(configuration);
    gravitinoCatalogManager.createCatalog(catalogName, type, "", provider, configuration.toMap());
  }

  @Override
  public void removeCatalog(String catalogName, boolean ignoreIfNotExists) throws CatalogException {
    gravitinoCatalogManager.dropCatalog(catalogName);
  }

  @Override
  public Optional<CatalogDescriptor> getCatalog(String catalogName) throws CatalogException {
    Catalog catalog = gravitinoCatalogManager.getGravitinoCatalogInfo(catalogName);
    CatalogDescriptor descriptor =
        CatalogDescriptor.of(catalogName, Configuration.fromMap(catalog.properties()));
    return Optional.of(descriptor);
  }

  @Override
  public Set<String> listCatalogs() throws CatalogException {
    return gravitinoCatalogManager.listCatalogs();
  }

  @Override
  public boolean contains(String catalogName) throws CatalogException {
    return gravitinoCatalogManager.contains(catalogName);
  }

  private String getCatalogProvider(Configuration configuration) {
    String catalogType =
        Preconditions.checkNotNull(
            configuration.get(CommonCatalogOptions.CATALOG_TYPE),
            "%s should not be null.",
            configuration.get(CommonCatalogOptions.CATALOG_TYPE));

    switch (catalogType) {
      case GravitinoHiveCatalogFactory.IDENTIFIER:
        return "hadoop";
      default:
        throw new UnsupportedOperationException(
            String.format("The catalog type is not supported:%s", catalogType));
    }
  }

  private Catalog.Type getGravitinoCatalogType(Configuration configuration) {
    String catalogType =
        Preconditions.checkNotNull(
            configuration.get(CommonCatalogOptions.CATALOG_TYPE),
            "%s should not be null.",
            configuration.get(CommonCatalogOptions.CATALOG_TYPE));

    switch (catalogType) {
      case GravitinoHiveCatalogFactory.IDENTIFIER:
        return Catalog.Type.RELATIONAL;
      default:
        throw new UnsupportedOperationException(
            String.format("The catalog type is not supported:%s", catalogType));
    }
  }
}
