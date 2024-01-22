/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.catalog.file;

import com.datastrato.gravitino.Entity;
import com.datastrato.gravitino.EntityStore;
import com.datastrato.gravitino.GravitinoEnv;
import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.Namespace;
import com.datastrato.gravitino.catalog.CatalogOperations;
import com.datastrato.gravitino.catalog.PropertiesMetadata;
import com.datastrato.gravitino.exceptions.FilesetAlreadyExistsException;
import com.datastrato.gravitino.exceptions.NoSuchCatalogException;
import com.datastrato.gravitino.exceptions.NoSuchEntityException;
import com.datastrato.gravitino.exceptions.NoSuchFilesetException;
import com.datastrato.gravitino.exceptions.NoSuchSchemaException;
import com.datastrato.gravitino.exceptions.NonEmptySchemaException;
import com.datastrato.gravitino.exceptions.SchemaAlreadyExistsException;
import com.datastrato.gravitino.file.Fileset;
import com.datastrato.gravitino.file.FilesetCatalog;
import com.datastrato.gravitino.file.FilesetChange;
import com.datastrato.gravitino.meta.AuditInfo;
import com.datastrato.gravitino.meta.CatalogEntity;
import com.datastrato.gravitino.meta.FilesetEntity;
import com.datastrato.gravitino.meta.SchemaEntity;
import com.datastrato.gravitino.rel.Schema;
import com.datastrato.gravitino.rel.SchemaChange;
import com.datastrato.gravitino.rel.SupportsSchemas;
import com.datastrato.gravitino.storage.IdGenerator;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.datastrato.gravitino.utils.PrincipalUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopCatalogOperations implements CatalogOperations, SupportsSchemas, FilesetCatalog {

  private static final HadoopCatalogPropertiesMetadata CATALOG_PROPERTIES_METADATA =
      new HadoopCatalogPropertiesMetadata();

  private static final HadoopSchemaPropertiesMetadata SCHEMA_PROPERTIES_METADATA =
      new HadoopSchemaPropertiesMetadata();

  private static final HadoopFilesetPropertiesMetadata FILESET_PROPERTIES_METADATA =
      new HadoopFilesetPropertiesMetadata();

  private final CatalogEntity entity;

  private final EntityStore store;

  private final IdGenerator idGenerator;

  public HadoopCatalogOperations(CatalogEntity entity, GravitinoEnv env) {
    this.entity = entity;
    this.store = env.entityStore();
    this.idGenerator = env.idGenerator();
  }

  public HadoopCatalogOperations(CatalogEntity entity) {
    this(entity, GravitinoEnv.getInstance());
  }

  @Override
  public void initialize(Map<String, String> config) throws RuntimeException {}

  @Override
  public NameIdentifier[] listFilesets(Namespace namespace) throws NoSuchSchemaException {
    try {
      List<FilesetEntity> filesets = store.list(namespace, FilesetEntity.class, Entity.EntityType.FILESET);
      return filesets.stream()
          .map(fileset -> NameIdentifier.of(namespace, fileset.name()))
          .toArray(NameIdentifier[]::new);
    } catch (IOException ioe) {
      throw new RuntimeException("Failed to list filesets under namespace " + namespace, ioe);
    }
  }

  @Override
  public Fileset loadFileset(NameIdentifier ident) throws NoSuchFilesetException {
    try {
      FilesetEntity filesetEntity = store.get(ident, Entity.EntityType.FILESET, FilesetEntity.class);
      return new HadoopFileset.Builder()
          .withName(filesetEntity.name())
          .withComment(filesetEntity.comment())
          .withAuditInfo(filesetEntity.auditInfo())
          .withProperties(filesetEntity.properties())
          .withType(filesetEntity.filesetType())
          .withStorageLocation(filesetEntity.storageLocation())
          .build();
    } catch (NoSuchFilesetException e) {
      throw new NoSuchFilesetException("Fileset " + ident.name() + "does not exist", e);
    } catch (IOException ioe) {
      throw new RuntimeException("Failed to load fileset " + ident.name(), ioe);
    }
  }

  @Override
  public Fileset createFileset(
      NameIdentifier ident,
      String comment,
      Fileset.Type type,
      String storageLocation,
      Map<String, String> properties)
      throws NoSuchSchemaException, FilesetAlreadyExistsException {
    NameIdentifier schemaIdent = NameIdentifier.of(ident.namespace().levels());
    try {
      if (!schemaExists(schemaIdent)) {
        throw new NoSuchSchemaException("Fileset schema does not exist " + schemaIdent);
      }

      if (store.exists(ident, Entity.EntityType.FILESET)) {
        throw new FilesetAlreadyExistsException("Fileset " + ident.name() + "already exists");
      }
    } catch (IOException ioe) {
      throw new RuntimeException("Failed to check if the fileset " + ident.name() + " exists", ioe);
    }

    Path filesetLocation = getPath(properties, storageLocation);

    if (filesetLocation != null) {
        try {
          // TODO: add hadoop conf
          FileSystem fs = filesetLocation.getFileSystem(null);
          if (!fs.exists(filesetLocation)) {
            fs.create(filesetLocation);
          }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    HadoopFileset fileset = new HadoopFileset.Builder()
            .withName(ident.name())
            .withStorageLocation(filesetLocation.getName())
            .withProperties(properties)
            .withType(type)
            .withAuditInfo(new AuditInfo.Builder()
                    .withCreator(PrincipalUtils.getCurrentPrincipal().getName())
                    .withCreateTime(Instant.now())
                    .build())
            .build();
    return null;
  }

  private Path getPath(Map<String, String> properties, String storageLocation) {
    if (storageLocation != null) {
      return new Path(storageLocation);
    }

    String schemaLocation =
            (String)
                    SCHEMA_PROPERTIES_METADATA.getOrDefault(
                            properties, HadoopSchemaPropertiesMetadata.LOCATION);
//    Optional.ofNullable(schemaLocation)
//            .map(Path::new)
//            .orElse();
    return null;
  }

  @Override
  public Fileset alterFileset(NameIdentifier ident, FilesetChange... changes)
      throws NoSuchFilesetException, IllegalArgumentException {
    try {
      if (!store.exists(ident, Entity.EntityType.FILESET)) {
        throw new NoSuchFilesetException("Fileset " + ident.name() + "does not exist");
      }
    } catch (IOException ioe) {
      throw new RuntimeException("Failed to alter fileset " + ident.name(), ioe);
    }

    try {
      FilesetEntity updated = store.update(
          ident,
          FilesetEntity.class,
          Entity.EntityType.FILESET,
          filesetEntity -> updateFilesetEntity(ident, filesetEntity, changes));
      return new HadoopFileset.Builder()
          .withName(updated.name())
          .withStorageLocation(updated.storageLocation())
          .withType(updated.filesetType())
          .withProperties(updated.properties())
          .withAuditInfo(updated.auditInfo())
          .withComment(updated.comment())
          .build();
    } catch (NoSuchEntityException nsee) {
      throw new NoSuchEntityException("No such fileset " + ident.name(), nsee);
    } catch (IOException ioe) {
      throw new RuntimeException("Failed to alter fileset " + ident.name(), ioe);
    }
  }


  @Override
  public boolean dropFileset(NameIdentifier ident) {

  }

  @Override
  public NameIdentifier[] listSchemas(Namespace namespace) throws NoSuchCatalogException {
    try {
      List<SchemaEntity> schemas =
          store.list(namespace, SchemaEntity.class, Entity.EntityType.SCHEMA);
      return schemas.stream()
          .map(s -> NameIdentifier.of(namespace, s.name()))
          .toArray(NameIdentifier[]::new);
    } catch (IOException e) {
      throw new RuntimeException("Failed to list schemas under namespace " + namespace, e);
    }
  }

  @Override
  public Schema createSchema(NameIdentifier ident, String comment, Map<String, String> properties)
      throws NoSuchCatalogException, SchemaAlreadyExistsException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Schema loadSchema(NameIdentifier ident) throws NoSuchSchemaException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Schema alterSchema(NameIdentifier ident, SchemaChange... changes)
      throws NoSuchSchemaException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean dropSchema(NameIdentifier ident, boolean cascade) throws NonEmptySchemaException {
    throw new UnsupportedOperationException();
  }

  @Override
  public PropertiesMetadata tablePropertiesMetadata() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "Hadoop fileset catalog doesn't support table related operations");
  }

  @Override
  public PropertiesMetadata catalogPropertiesMetadata() throws UnsupportedOperationException {
    return CATALOG_PROPERTIES_METADATA;
  }

  @Override
  public PropertiesMetadata schemaPropertiesMetadata() throws UnsupportedOperationException {
    return SCHEMA_PROPERTIES_METADATA;
  }

  @Override
  public PropertiesMetadata filesetPropertiesMetadata() throws UnsupportedOperationException {
    return FILESET_PROPERTIES_METADATA;
  }

  @Override
  public void close() throws IOException {}

  private FilesetEntity updateFilesetEntity(NameIdentifier ident, FilesetEntity filesetEntity,
      FilesetChange... changes) {
    return null;
  }
}
