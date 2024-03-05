import typing_extensions

from pygravitino.paths import PathValues
from pygravitino.apis.paths.metalakes import Metalakes
from pygravitino.apis.paths.metalakes_name import MetalakesName
from pygravitino.apis.paths.metalakes_metalake_catalogs import MetalakesMetalakeCatalogs
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog import MetalakesMetalakeCatalogsCatalog
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog_schemas import MetalakesMetalakeCatalogsCatalogSchemas
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog_schemas_schema import MetalakesMetalakeCatalogsCatalogSchemasSchema
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog_schemas_schema_tables import MetalakesMetalakeCatalogsCatalogSchemasSchemaTables
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog_schemas_schema_tables_table import MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTable
from pygravitino.apis.paths.metalaskes_metalake_catalogs_catalog_schemas_schema_tables_table_partitions import MetalaskesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitions
from pygravitino.apis.paths.metalakes_metalake_catalogs_catalog_schemas_schema_tables_table_partitions_partition import MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitionsPartition

PathToApi = typing_extensions.TypedDict(
    'PathToApi',
    {
        PathValues.METALAKES: Metalakes,
        PathValues.METALAKES_NAME: MetalakesName,
        PathValues.METALAKES_METALAKE_CATALOGS: MetalakesMetalakeCatalogs,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG: MetalakesMetalakeCatalogsCatalog,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS: MetalakesMetalakeCatalogsCatalogSchemas,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA: MetalakesMetalakeCatalogsCatalogSchemasSchema,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES: MetalakesMetalakeCatalogsCatalogSchemasSchemaTables,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE: MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTable,
        PathValues.METALASKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS: MetalaskesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitions,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS_PARTITION: MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitionsPartition,
    }
)

path_to_api = PathToApi(
    {
        PathValues.METALAKES: Metalakes,
        PathValues.METALAKES_NAME: MetalakesName,
        PathValues.METALAKES_METALAKE_CATALOGS: MetalakesMetalakeCatalogs,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG: MetalakesMetalakeCatalogsCatalog,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS: MetalakesMetalakeCatalogsCatalogSchemas,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA: MetalakesMetalakeCatalogsCatalogSchemasSchema,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES: MetalakesMetalakeCatalogsCatalogSchemasSchemaTables,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE: MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTable,
        PathValues.METALASKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS: MetalaskesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitions,
        PathValues.METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS_PARTITION: MetalakesMetalakeCatalogsCatalogSchemasSchemaTablesTablePartitionsPartition,
    }
)
