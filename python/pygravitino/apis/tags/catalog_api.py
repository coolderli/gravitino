# coding: utf-8

"""
    Gravitino REST API

    Defines the specification for the first version of the Gravitino REST API.   # noqa: E501

    The version of the OpenAPI document: 0.4.0
    Generated by: https://openapi-generator.tech
"""

from pygravitino.paths.metalakes_metalake_catalogs_catalog.put import AlterCatalog
from pygravitino.paths.metalakes_metalake_catalogs.post import CreateCatalog
from pygravitino.paths.metalakes_metalake_catalogs_catalog.delete import DropCatalog
from pygravitino.paths.metalakes_metalake_catalogs.get import ListCatalogs
from pygravitino.paths.metalakes_metalake_catalogs_catalog.get import LoadCatalog


class CatalogApi(
    AlterCatalog,
    CreateCatalog,
    DropCatalog,
    ListCatalogs,
    LoadCatalog,
):
    """NOTE: This class is auto generated by OpenAPI Generator
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """
    pass
