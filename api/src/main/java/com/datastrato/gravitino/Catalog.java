/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino;

import com.datastrato.gravitino.file.FilesetCatalog;
import com.datastrato.gravitino.rel.SupportsSchemas;
import com.datastrato.gravitino.rel.TableCatalog;
import java.util.Map;

/**
 * The interface of a catalog. The catalog is the second level entity in the gravitino system,
 * containing a set of tables.
 */
public interface Catalog extends Auditable {

  /** The type of the catalog. */
  enum Type {
    /** Catalog Type for Relational Data Structure, like db.table, catalog.db.table. */
    RELATIONAL,

    /** Catalog Type for File System (including HDFS, S3, etc.), like path/to/file */
    FILE,

    /** Catalog Type for Message Queue, like kafka://topic */
    STREAM,
  }

  /**
   * A reserved property to specify the package location of the catalog. The "package" is a string
   * of path to the folder where all the catalog related dependencies is located. The dependencies
   * under the "package" will be loaded by Gravitino to create the catalog.
   *
   * <p>The property "package" is not needed if the catalog is a built-in one, Gravitino will search
   * the proper location using "provider" to load the dependencies. Only when the folder is in
   * different location, the "package" property is needed.
   */
  String PROPERTY_PACKAGE = "package";

  /**
   * Return the name of the catalog.
   *
   * @return The name of the catalog.
   */
  String name();

  /**
   * Return the type of the catalog.
   *
   * @return The type of the catalog.
   */
  Type type();

  /**
   * Return the provider of the catalog.
   *
   * @return The provider of the catalog.
   */
  String provider();

  /**
   * The comment of the catalog. Note. this method will return null if the comment is not set for
   * this catalog.
   *
   * @return The comment of the catalog.
   */
  String comment();

  /**
   * The properties of the catalog. Note, this method will return null if the properties are not
   * set.
   *
   * @return The properties of the catalog.
   */
  Map<String, String> properties();

  /**
   * Return the {@link SupportsSchemas} if the catalog supports schema operations.
   *
   * @throws UnsupportedOperationException if the catalog does not support schema operations.
   * @return The {@link SupportsSchemas} if the catalog supports schema operations.
   */
  default SupportsSchemas asSchemas() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Catalog does not support schema operations");
  }

  /**
   * Return the {@link TableCatalog} if the catalog supports table operations.
   *
   * @return the {@link TableCatalog} if the catalog supports table operations.
   * @throws UnsupportedOperationException if the catalog does not support table operations.
   */
  default TableCatalog asTableCatalog() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Catalog does not support table operations");
  }

  /**
   * Return the {@link FilesetCatalog} if the catalog supports fileset operations.
   *
   * @return the {@link FilesetCatalog} if the catalog supports fileset operations.
   * @throws UnsupportedOperationException if the catalog does not support fileset operations.
   */
  default FilesetCatalog asFilesetCatalog() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Catalog does not support fileset operations");
  }
}
