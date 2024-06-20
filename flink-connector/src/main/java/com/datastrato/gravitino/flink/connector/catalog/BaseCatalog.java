/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.catalog;

import com.datastrato.gravitino.Catalog;
import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.Namespace;
import com.datastrato.gravitino.Schema;
import com.datastrato.gravitino.SchemaChange;
import com.datastrato.gravitino.exceptions.NoSuchCatalogException;
import com.datastrato.gravitino.exceptions.NoSuchSchemaException;
import com.datastrato.gravitino.exceptions.NonEmptySchemaException;
import com.datastrato.gravitino.exceptions.SchemaAlreadyExistsException;
import com.datastrato.gravitino.exceptions.TableAlreadyExistsException;
import com.datastrato.gravitino.flink.connector.PropertiesConverter;
import com.datastrato.gravitino.flink.connector.utils.TableUtils;
import com.datastrato.gravitino.flink.connector.utils.TypeUtils;
import com.datastrato.gravitino.rel.Column;
import com.datastrato.gravitino.rel.Table;
import com.datastrato.gravitino.rel.TableChange;
import com.datastrato.gravitino.rel.expressions.transforms.Transform;
import com.datastrato.gravitino.rel.expressions.transforms.Transforms;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.compress.utils.Lists;
import org.apache.flink.table.catalog.AbstractCatalog;
import org.apache.flink.table.catalog.CatalogBaseTable;
import org.apache.flink.table.catalog.CatalogDatabase;
import org.apache.flink.table.catalog.CatalogDatabaseImpl;
import org.apache.flink.table.catalog.CatalogFunction;
import org.apache.flink.table.catalog.CatalogPartition;
import org.apache.flink.table.catalog.CatalogPartitionSpec;
import org.apache.flink.table.catalog.CatalogTable;
import org.apache.flink.table.catalog.ObjectPath;
import org.apache.flink.table.catalog.ResolvedCatalogBaseTable;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotEmptyException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.catalog.exceptions.FunctionAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.FunctionNotExistException;
import org.apache.flink.table.catalog.exceptions.PartitionAlreadyExistsException;
import org.apache.flink.table.catalog.exceptions.PartitionNotExistException;
import org.apache.flink.table.catalog.exceptions.PartitionSpecInvalidException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.table.catalog.exceptions.TableNotPartitionedException;
import org.apache.flink.table.catalog.exceptions.TablePartitionedException;
import org.apache.flink.table.catalog.stats.CatalogColumnStatistics;
import org.apache.flink.table.catalog.stats.CatalogTableStatistics;
import org.apache.flink.table.expressions.Expression;

/**
 * The BaseCatalog that provides a default implementation for all methods in the {@link
 * org.apache.flink.table.catalog.Catalog} interface.
 */
public abstract class BaseCatalog extends AbstractCatalog {
  private final PropertiesConverter propertiesConverter;

  protected BaseCatalog(String catalogName, String defaultDatabase) {
    super(catalogName, defaultDatabase);
    this.propertiesConverter = getPropertiesConverter();
  }

  @Override
  public void open() throws CatalogException {}

  @Override
  public void close() throws CatalogException {}

  @Override
  public List<String> listDatabases() throws CatalogException {
    return Arrays.asList(catalog().asSchemas().listSchemas());
  }

  @Override
  public CatalogDatabase getDatabase(String databaseName)
      throws DatabaseNotExistException, CatalogException {
    try {
      Schema schema = catalog().asSchemas().loadSchema(databaseName);
      Map<String, String> properties =
          propertiesConverter.toFlinkSchemaProperties(schema.properties());
      return new CatalogDatabaseImpl(properties, schema.comment());
    } catch (NoSuchSchemaException e) {
      throw new DatabaseNotExistException(catalogName(), databaseName);
    }
  }

  @Override
  public boolean databaseExists(String databaseName) throws CatalogException {
    return catalog().asSchemas().schemaExists(databaseName);
  }

  @Override
  public void createDatabase(
      String databaseName, CatalogDatabase catalogDatabase, boolean ignoreIfExists)
      throws DatabaseAlreadyExistException, CatalogException {
    try {
      Map<String, String> properties =
          propertiesConverter.toGravitinoSchemaProperties(catalogDatabase.getProperties());
      catalog().asSchemas().createSchema(databaseName, catalogDatabase.getComment(), properties);
    } catch (SchemaAlreadyExistsException e) {
      if (!ignoreIfExists) {
        throw new DatabaseAlreadyExistException(catalogName(), databaseName);
      }
    } catch (NoSuchCatalogException e) {
      throw new CatalogException(e);
    }
  }

  @Override
  public void dropDatabase(String databaseName, boolean ignoreIfNotExists, boolean cascade)
      throws DatabaseNotExistException, DatabaseNotEmptyException, CatalogException {
    try {
      boolean dropped = catalog().asSchemas().dropSchema(databaseName, cascade);
      if (!dropped && !ignoreIfNotExists) {
        throw new DatabaseNotExistException(catalogName(), databaseName);
      }
    } catch (NonEmptySchemaException e) {
      throw new DatabaseNotEmptyException(catalogName(), databaseName);
    } catch (NoSuchCatalogException e) {
      throw new CatalogException(e);
    }
  }

  @Override
  public void alterDatabase(
      String databaseName, CatalogDatabase catalogDatabase, boolean ignoreIfNotExists)
      throws DatabaseNotExistException, CatalogException {
    try {
      SchemaChange[] schemaChanges = getSchemaChange(getDatabase(databaseName), catalogDatabase);
      catalog().asSchemas().alterSchema(databaseName, schemaChanges);
    } catch (NoSuchSchemaException e) {
      if (!ignoreIfNotExists) {
        throw new DatabaseNotExistException(catalogName(), databaseName);
      }
    } catch (NoSuchCatalogException e) {
      throw new CatalogException(e);
    }
  }

  @Override
  public List<String> listTables(String databaseName)
      throws DatabaseNotExistException, CatalogException {
    try {
      return Stream.of(
              catalog()
                  .asTableCatalog()
                  .listTables(Namespace.of(metalakeName(), catalogName(), databaseName)))
          .map(NameIdentifier::name)
          .collect(Collectors.toList());
    } catch (NoSuchSchemaException e) {
      throw new DatabaseNotExistException(catalogName(), databaseName);
    }
  }

  @Override
  public List<String> listViews(String s) throws DatabaseNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogBaseTable getTable(ObjectPath tablePath)
      throws TableNotExistException, CatalogException {
    try {
      Table table =
          catalog()
              .asTableCatalog()
              .loadTable(
                  NameIdentifier.of(
                      metalakeName(),
                      catalogName(),
                      tablePath.getDatabaseName(),
                      tablePath.getObjectName()));
      return toFlinkTable(table);
    } catch (NoSuchCatalogException e) {
      throw new CatalogException(e);
    }
  }

  @Override
  public boolean tableExists(ObjectPath tablePath) throws CatalogException {
    return catalog()
        .asTableCatalog()
        .tableExists(
            NameIdentifier.of(
                metalakeName(),
                catalogName(),
                tablePath.getDatabaseName(),
                tablePath.getObjectName()));
  }

  @Override
  public void dropTable(ObjectPath tablePath, boolean ignoreIfNotExists)
      throws TableNotExistException, CatalogException {
    boolean dropped =
        catalog()
            .asTableCatalog()
            .dropTable(
                NameIdentifier.of(
                    metalakeName(),
                    catalogName(),
                    tablePath.getDatabaseName(),
                    tablePath.getObjectName()));
    if (!dropped && !ignoreIfNotExists) {
      throw new TableNotExistException(catalogName(), tablePath);
    }
  }

  @Override
  public void renameTable(ObjectPath tablePath, String newTableName, boolean ignoreIfNotExists)
      throws TableNotExistException, TableAlreadyExistException, CatalogException {
    NameIdentifier identifier =
        NameIdentifier.of(
            Namespace.of(metalakeName(), catalogName(), tablePath.getDatabaseName()), newTableName);

    if (catalog().asTableCatalog().tableExists(identifier)) {
      throw new TableAlreadyExistException(
          catalogName(), ObjectPath.fromString(tablePath.getDatabaseName() + newTableName));
    }

    try {
      catalog()
          .asTableCatalog()
          .alterTable(
              NameIdentifier.of(
                  metalakeName(),
                  catalogName(),
                  tablePath.getDatabaseName(),
                  tablePath.getObjectName()),
              TableChange.rename(newTableName));
    } catch (NoSuchCatalogException e) {
      if (!ignoreIfNotExists) {
        throw new TableNotExistException(catalogName(), tablePath);
      }
    }
  }

  @Override
  public void createTable(ObjectPath tablePath, CatalogBaseTable table, boolean ignoreIfExists)
      throws TableAlreadyExistException, DatabaseNotExistException, CatalogException {
    NameIdentifier identifier =
        NameIdentifier.of(
            metalakeName(), catalogName(), tablePath.getDatabaseName(), tablePath.getObjectName());

    ResolvedCatalogBaseTable<?> resolvedTable = (ResolvedCatalogBaseTable<?>) table;
    Column[] columns =
        resolvedTable.getResolvedSchema().getColumns().stream()
            .map(this::toGravitinoColumn)
            .toArray(Column[]::new);
    String comment = table.getComment();
    Map<String, String> properties =
        propertiesConverter.toGravitinoTableProperties(table.getOptions());
    Transform[] partitions =
        ((CatalogTable) table)
            .getPartitionKeys().stream().map(Transforms::identity).toArray(Transform[]::new);
    try {
      catalog().asTableCatalog().createTable(identifier, columns, comment, properties, partitions);
    } catch (NoSuchSchemaException e) {
      throw new DatabaseNotExistException(catalogName(), tablePath.getDatabaseName(), e);
    } catch (TableAlreadyExistsException e) {
      if (!ignoreIfExists) {
        throw new TableAlreadyExistException(catalogName(), tablePath, e);
      }
    } catch (Exception e) {
      throw new CatalogException(e);
    }
  }

  /**
   * The method only is used to change the properties and comments. To alter columns, use the other
   * alterTable API and provide a list of TableChange's.
   *
   * @param tablePath path of the table or view to be modified
   * @param newTable the new table definition
   * @param ignoreIfNotExists flag to specify behavior when the table or view does not exist: if set
   *     to false, throw an exception, if set to true, do nothing.
   * @throws TableNotExistException if the table not exists.
   * @throws CatalogException in case of any runtime exception.
   */
  @Override
  public void alterTable(ObjectPath tablePath, CatalogBaseTable newTable, boolean ignoreIfNotExists)
      throws TableNotExistException, CatalogException {
    CatalogBaseTable existingTable;
    try {
      existingTable = this.getTable(tablePath);
    } catch (TableNotExistException e) {
      if (!ignoreIfNotExists) {
        throw e;
      }
      return;
    }

    if (existingTable.getTableKind() != newTable.getTableKind()) {
      throw new CatalogException(
          String.format(
              "Table types don't match. Existing table is '%s' and new table is '%s'.",
              existingTable.getTableKind(), newTable.getTableKind()));
    }

    List<TableChange> changes = Lists.newArrayList();
    changes.addAll(optionsChanges(existingTable.getOptions(), newTable.getOptions()));
    if (!Objects.equals(newTable.getComment(), existingTable.getComment())) {
      changes.add(TableChange.updateComment(newTable.getComment()));
    }

    NameIdentifier identifier =
        NameIdentifier.of(
            metalakeName(), catalogName(), tablePath.getDatabaseName(), tablePath.getObjectName());
    catalog().asTableCatalog().alterTable(identifier, changes.toArray(new TableChange[0]));
  }

  @Override
  public void alterTable(
      ObjectPath tablePath,
      CatalogBaseTable newTable,
      List<org.apache.flink.table.catalog.TableChange> tableChanges,
      boolean ignoreIfNotExists)
      throws TableNotExistException, CatalogException {
    CatalogBaseTable existingTable;
    try {
      existingTable = this.getTable(tablePath);
    } catch (TableNotExistException e) {
      if (!ignoreIfNotExists) {
        throw e;
      }
      return;
    }

    if (existingTable.getTableKind() != newTable.getTableKind()) {
      throw new CatalogException(
          String.format(
              "Table types don't match. Existing table is '%s' and new table is '%s'.",
              existingTable.getTableKind(), newTable.getTableKind()));
    }

    NameIdentifier identifier =
        NameIdentifier.of(
            metalakeName(), catalogName(), tablePath.getDatabaseName(), tablePath.getObjectName());
    List<TableChange> changes = Lists.newArrayList();
    for (org.apache.flink.table.catalog.TableChange change : tableChanges) {
      if (change instanceof org.apache.flink.table.catalog.TableChange.AddColumn) {
        addColumn((org.apache.flink.table.catalog.TableChange.AddColumn) change, changes);
      } else if (change instanceof org.apache.flink.table.catalog.TableChange.DropColumn) {
        dropColumn((org.apache.flink.table.catalog.TableChange.DropColumn) change, changes);
      } else if (change instanceof org.apache.flink.table.catalog.TableChange.ModifyColumn) {
        modifyColumn(change, changes);
      } else if (change instanceof org.apache.flink.table.catalog.TableChange.SetOption) {
        setProperty((org.apache.flink.table.catalog.TableChange.SetOption) change, changes);
      } else if (change instanceof org.apache.flink.table.catalog.TableChange.ResetOption) {
        removeProperty((org.apache.flink.table.catalog.TableChange.ResetOption) change, changes);
      } else {
        throw new UnsupportedOperationException(
            String.format("Not supported change : %s", change.getClass()));
      }
    }
    catalog().asTableCatalog().alterTable(identifier, changes.toArray(new TableChange[0]));
  }

  private List<TableChange> optionsChanges(
      Map<String, String> currentOptions, Map<String, String> updatedOptions) {
    List<TableChange> optionsChanges = com.google.common.collect.Lists.newArrayList();
    MapDifference<String, String> difference = Maps.difference(currentOptions, updatedOptions);
    difference
        .entriesOnlyOnLeft()
        .forEach((key, value) -> optionsChanges.add(TableChange.removeProperty(key)));
    difference
        .entriesOnlyOnRight()
        .forEach((key, value) -> optionsChanges.add(TableChange.setProperty(key, value)));
    difference
        .entriesDiffering()
        .forEach(
            (key, value) -> {
              optionsChanges.add(TableChange.setProperty(key, value.rightValue()));
            });
    return optionsChanges;
  }

  private void removeProperty(
      org.apache.flink.table.catalog.TableChange.ResetOption change, List<TableChange> changes) {
    changes.add(TableChange.removeProperty(change.getKey()));
  }

  private void setProperty(
      org.apache.flink.table.catalog.TableChange.SetOption change, List<TableChange> changes) {
    changes.add(TableChange.setProperty(change.getKey(), change.getValue()));
  }

  private void dropColumn(
      org.apache.flink.table.catalog.TableChange.DropColumn change, List<TableChange> changes) {
    changes.add(TableChange.deleteColumn(new String[] {change.getColumnName()}, false));
  }

  private void addColumn(
      org.apache.flink.table.catalog.TableChange.AddColumn change, List<TableChange> changes) {
    changes.add(
        TableChange.addColumn(
            new String[] {change.getColumn().getName()},
            TypeUtils.toGravitinoType(change.getColumn().getDataType().getLogicalType()),
            change.getColumn().getComment().orElse(null),
            TableUtils.toGravitinoColumnPosition(change.getPosition())));
  }

  private void modifyColumn(
      org.apache.flink.table.catalog.TableChange change, List<TableChange> changes) {
    if (change instanceof org.apache.flink.table.catalog.TableChange.ModifyColumnName) {
      org.apache.flink.table.catalog.TableChange.ModifyColumnName modifyColumnName =
          (org.apache.flink.table.catalog.TableChange.ModifyColumnName) change;
      changes.add(
          TableChange.renameColumn(
              new String[] {modifyColumnName.getOldColumnName()},
              modifyColumnName.getNewColumnName()));
    } else if (change
        instanceof org.apache.flink.table.catalog.TableChange.ModifyPhysicalColumnType) {
      org.apache.flink.table.catalog.TableChange.ModifyPhysicalColumnType modifyColumnType =
          (org.apache.flink.table.catalog.TableChange.ModifyPhysicalColumnType) change;
      changes.add(
          TableChange.updateColumnType(
              new String[] {modifyColumnType.getOldColumn().getName()},
              TypeUtils.toGravitinoType(modifyColumnType.getNewType().getLogicalType())));
    } else if (change instanceof org.apache.flink.table.catalog.TableChange.ModifyColumnPosition) {
      org.apache.flink.table.catalog.TableChange.ModifyColumnPosition modifyColumnPosition =
          (org.apache.flink.table.catalog.TableChange.ModifyColumnPosition) change;
      changes.add(
          TableChange.updateColumnPosition(
              new String[] {modifyColumnPosition.getOldColumn().getName()},
              TableUtils.toGravitinoColumnPosition(modifyColumnPosition.getNewPosition())));
    } else if (change instanceof org.apache.flink.table.catalog.TableChange.ModifyColumnComment) {
      org.apache.flink.table.catalog.TableChange.ModifyColumnComment modifyColumnComment =
          (org.apache.flink.table.catalog.TableChange.ModifyColumnComment) change;
      changes.add(
          TableChange.updateColumnComment(
              new String[] {modifyColumnComment.getOldColumn().getName()},
              modifyColumnComment.getNewComment()));
    } else {
      throw new IllegalArgumentException(
          String.format("Not support ModifyColumn : %s", change.getClass()));
    }
  }

  @Override
  public List<CatalogPartitionSpec> listPartitions(ObjectPath objectPath)
      throws TableNotExistException, TableNotPartitionedException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<CatalogPartitionSpec> listPartitions(
      ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec)
      throws TableNotExistException, TableNotPartitionedException, PartitionSpecInvalidException,
          CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<CatalogPartitionSpec> listPartitionsByFilter(
      ObjectPath objectPath, List<Expression> list)
      throws TableNotExistException, TableNotPartitionedException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogPartition getPartition(
      ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean partitionExists(ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec)
      throws CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createPartition(
      ObjectPath objectPath,
      CatalogPartitionSpec catalogPartitionSpec,
      CatalogPartition catalogPartition,
      boolean b)
      throws TableNotExistException, TableNotPartitionedException, PartitionSpecInvalidException,
          PartitionAlreadyExistsException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void dropPartition(
      ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec, boolean b)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterPartition(
      ObjectPath objectPath,
      CatalogPartitionSpec catalogPartitionSpec,
      CatalogPartition catalogPartition,
      boolean b)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> listFunctions(String s) throws DatabaseNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogFunction getFunction(ObjectPath objectPath)
      throws FunctionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean functionExists(ObjectPath objectPath) throws CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createFunction(ObjectPath objectPath, CatalogFunction catalogFunction, boolean b)
      throws FunctionAlreadyExistException, DatabaseNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterFunction(ObjectPath objectPath, CatalogFunction catalogFunction, boolean b)
      throws FunctionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void dropFunction(ObjectPath objectPath, boolean b)
      throws FunctionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogTableStatistics getTableStatistics(ObjectPath objectPath)
      throws TableNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogColumnStatistics getTableColumnStatistics(ObjectPath objectPath)
      throws TableNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogTableStatistics getPartitionStatistics(
      ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CatalogColumnStatistics getPartitionColumnStatistics(
      ObjectPath objectPath, CatalogPartitionSpec catalogPartitionSpec)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterTableStatistics(
      ObjectPath objectPath, CatalogTableStatistics catalogTableStatistics, boolean b)
      throws TableNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterTableColumnStatistics(
      ObjectPath objectPath, CatalogColumnStatistics catalogColumnStatistics, boolean b)
      throws TableNotExistException, CatalogException, TablePartitionedException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterPartitionStatistics(
      ObjectPath objectPath,
      CatalogPartitionSpec catalogPartitionSpec,
      CatalogTableStatistics catalogTableStatistics,
      boolean b)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void alterPartitionColumnStatistics(
      ObjectPath objectPath,
      CatalogPartitionSpec catalogPartitionSpec,
      CatalogColumnStatistics catalogColumnStatistics,
      boolean b)
      throws PartitionNotExistException, CatalogException {
    throw new UnsupportedOperationException();
  }

  protected abstract PropertiesConverter getPropertiesConverter();

  protected CatalogBaseTable toFlinkTable(Table table) {

    org.apache.flink.table.api.Schema.Builder builder =
        org.apache.flink.table.api.Schema.newBuilder();
    for (Column column : table.columns()) {
      builder
          .column(column.name(), TypeUtils.toFlinkType(column.dataType()))
          .withComment(column.name());
    }

    List<String> partitionKeys =
        Arrays.stream(table.partitioning()).map(Transform::name).collect(Collectors.toList());
    return CatalogTable.of(builder.build(), table.comment(), partitionKeys, table.properties());
  }

  private Column toGravitinoColumn(org.apache.flink.table.catalog.Column column) {
    return Column.of(
        column.getName(),
        TypeUtils.toGravitinoType(column.getDataType().getLogicalType()),
        column.getComment().orElse(null),
        column.getDataType().getLogicalType().isNullable(),
        false,
        null);
  }

  @VisibleForTesting
  static SchemaChange[] getSchemaChange(CatalogDatabase current, CatalogDatabase updated) {
    Map<String, String> currentProperties = current.getProperties();
    Map<String, String> updatedProperties = updated.getProperties();

    List<SchemaChange> schemaChanges = Lists.newArrayList();
    MapDifference<String, String> difference =
        Maps.difference(currentProperties, updatedProperties);
    difference
        .entriesOnlyOnLeft()
        .forEach((key, value) -> schemaChanges.add(SchemaChange.removeProperty(key)));
    difference
        .entriesOnlyOnRight()
        .forEach((key, value) -> schemaChanges.add(SchemaChange.setProperty(key, value)));
    difference
        .entriesDiffering()
        .forEach(
            (key, value) -> {
              schemaChanges.add(SchemaChange.setProperty(key, value.rightValue()));
            });
    return schemaChanges.toArray(new SchemaChange[0]);
  }

  private Catalog catalog() {
    return GravitinoCatalogManager.get().getGravitinoCatalogInfo(getName());
  }

  private String catalogName() {
    return getName();
  }

  private String metalakeName() {
    return GravitinoCatalogManager.get().getMetalakeName();
  }
}
