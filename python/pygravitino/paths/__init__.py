# do not import all endpoints into this module because that uses a lot of memory and stack frames
# if you need the ability to import all endpoints from this module, import them with
# from pygravitino.apis.path_to_api import path_to_api

import enum


class PathValues(str, enum.Enum):
    METALAKES = "/metalakes"
    METALAKES_NAME = "/metalakes/{name}"
    METALAKES_METALAKE_CATALOGS = "/metalakes/{metalake}/catalogs"
    METALAKES_METALAKE_CATALOGS_CATALOG = "/metalakes/{metalake}/catalogs/{catalog}"
    METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS = "/metalakes/{metalake}/catalogs/{catalog}/schemas"
    METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA = "/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}"
    METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES = "/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables"
    METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE = "/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}"
    METALASKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS = "/metalaskes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}/partitions"
    METALAKES_METALAKE_CATALOGS_CATALOG_SCHEMAS_SCHEMA_TABLES_TABLE_PARTITIONS_PARTITION = "/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}/partitions/{partition}"
