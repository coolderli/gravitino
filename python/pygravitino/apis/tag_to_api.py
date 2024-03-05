import typing_extensions

from pygravitino.apis.tags import TagValues
from pygravitino.apis.tags.catalog_api import CatalogApi
from pygravitino.apis.tags.metalake_api import MetalakeApi
from pygravitino.apis.tags.partition_api import PartitionApi
from pygravitino.apis.tags.schema_api import SchemaApi
from pygravitino.apis.tags.table_api import TableApi

TagToApi = typing_extensions.TypedDict(
    'TagToApi',
    {
        TagValues.CATALOG: CatalogApi,
        TagValues.METALAKE: MetalakeApi,
        TagValues.PARTITION: PartitionApi,
        TagValues.SCHEMA: SchemaApi,
        TagValues.TABLE: TableApi,
    }
)

tag_to_api = TagToApi(
    {
        TagValues.CATALOG: CatalogApi,
        TagValues.METALAKE: MetalakeApi,
        TagValues.PARTITION: PartitionApi,
        TagValues.SCHEMA: SchemaApi,
        TagValues.TABLE: TableApi,
    }
)
