# coding: utf-8

"""
    Gravitino REST API

    Defines the specification for the first version of the Gravitino REST API.   # noqa: E501

    The version of the OpenAPI document: 0.4.0
    Generated by: https://openapi-generator.tech
"""

from datetime import date, datetime  # noqa: F401
import decimal  # noqa: F401
import functools  # noqa: F401
import io  # noqa: F401
import re  # noqa: F401
import typing  # noqa: F401
import typing_extensions  # noqa: F401
import uuid  # noqa: F401

import frozendict  # noqa: F401

from pygravitino import schemas  # noqa: F401


class TableCreateRequest(
    schemas.DictSchema
):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """


    class MetaOapg:
        required = {
            "columns",
            "name",
        }
        
        class properties:
            name = schemas.StrSchema
            
            
            class columns(
                schemas.ListSchema
            ):
            
            
                class MetaOapg:
                    
                    @staticmethod
                    def items() -> typing.Type['Column']:
                        return Column
            
                def __new__(
                    cls,
                    _arg: typing.Union[typing.Tuple['Column'], typing.List['Column']],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'columns':
                    return super().__new__(
                        cls,
                        _arg,
                        _configuration=_configuration,
                    )
            
                def __getitem__(self, i: int) -> 'Column':
                    return super().__getitem__(i)
            
            
            class comment(
                schemas.StrBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneStrMixin
            ):
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[None, str, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'comment':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
            
            
            class properties(
                schemas.DictBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneFrozenDictMixin
            ):
            
            
                class MetaOapg:
                    additional_properties = schemas.StrSchema
            
                
                def __getitem__(self, name: typing.Union[str, ]) -> MetaOapg.additional_properties:
                    # dict_instance[name] accessor
                    return super().__getitem__(name)
                
                def get_item_oapg(self, name: typing.Union[str, ]) -> MetaOapg.additional_properties:
                    return super().get_item_oapg(name)
            
                def __new__(
                    cls,
                    *_args: typing.Union[dict, frozendict.frozendict, None, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                    **kwargs: typing.Union[MetaOapg.additional_properties, str, ],
                ) -> 'properties':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                        **kwargs,
                    )
            
            
            class sortOrders(
                schemas.ListBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneTupleMixin
            ):
            
            
                class MetaOapg:
                    
                    @staticmethod
                    def items() -> typing.Type['SortOrder']:
                        return SortOrder
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[list, tuple, None, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'sortOrders':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
        
            @staticmethod
            def distribution() -> typing.Type['Distribution']:
                return Distribution
            
            
            class partitioning(
                schemas.ListBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneTupleMixin
            ):
            
            
                class MetaOapg:
                    
                    @staticmethod
                    def items() -> typing.Type['PartitioningSpec']:
                        return PartitioningSpec
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[list, tuple, None, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'partitioning':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
            
            
            class indexes(
                schemas.ListBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneTupleMixin
            ):
            
            
                class MetaOapg:
                    
                    @staticmethod
                    def items() -> typing.Type['IndexSpec']:
                        return IndexSpec
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[list, tuple, None, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'indexes':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
            __annotations__ = {
                "name": name,
                "columns": columns,
                "comment": comment,
                "properties": properties,
                "sortOrders": sortOrders,
                "distribution": distribution,
                "partitioning": partitioning,
                "indexes": indexes,
            }
    
    columns: MetaOapg.properties.columns
    name: MetaOapg.properties.name
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["name"]) -> MetaOapg.properties.name: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["columns"]) -> MetaOapg.properties.columns: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["comment"]) -> MetaOapg.properties.comment: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["properties"]) -> MetaOapg.properties.properties: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["sortOrders"]) -> MetaOapg.properties.sortOrders: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["distribution"]) -> 'Distribution': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["partitioning"]) -> MetaOapg.properties.partitioning: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["indexes"]) -> MetaOapg.properties.indexes: ...
    
    @typing.overload
    def __getitem__(self, name: str) -> schemas.UnsetAnyTypeSchema: ...
    
    def __getitem__(self, name: typing.Union[typing_extensions.Literal["name", "columns", "comment", "properties", "sortOrders", "distribution", "partitioning", "indexes", ], str]):
        # dict_instance[name] accessor
        return super().__getitem__(name)
    
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["name"]) -> MetaOapg.properties.name: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["columns"]) -> MetaOapg.properties.columns: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["comment"]) -> typing.Union[MetaOapg.properties.comment, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["properties"]) -> typing.Union[MetaOapg.properties.properties, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["sortOrders"]) -> typing.Union[MetaOapg.properties.sortOrders, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["distribution"]) -> typing.Union['Distribution', schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["partitioning"]) -> typing.Union[MetaOapg.properties.partitioning, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["indexes"]) -> typing.Union[MetaOapg.properties.indexes, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: str) -> typing.Union[schemas.UnsetAnyTypeSchema, schemas.Unset]: ...
    
    def get_item_oapg(self, name: typing.Union[typing_extensions.Literal["name", "columns", "comment", "properties", "sortOrders", "distribution", "partitioning", "indexes", ], str]):
        return super().get_item_oapg(name)
    

    def __new__(
        cls,
        *_args: typing.Union[dict, frozendict.frozendict, ],
        columns: typing.Union[MetaOapg.properties.columns, list, tuple, ],
        name: typing.Union[MetaOapg.properties.name, str, ],
        comment: typing.Union[MetaOapg.properties.comment, None, str, schemas.Unset] = schemas.unset,
        properties: typing.Union[MetaOapg.properties.properties, dict, frozendict.frozendict, None, schemas.Unset] = schemas.unset,
        sortOrders: typing.Union[MetaOapg.properties.sortOrders, list, tuple, None, schemas.Unset] = schemas.unset,
        distribution: typing.Union['Distribution', schemas.Unset] = schemas.unset,
        partitioning: typing.Union[MetaOapg.properties.partitioning, list, tuple, None, schemas.Unset] = schemas.unset,
        indexes: typing.Union[MetaOapg.properties.indexes, list, tuple, None, schemas.Unset] = schemas.unset,
        _configuration: typing.Optional[schemas.Configuration] = None,
        **kwargs: typing.Union[schemas.AnyTypeSchema, dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, None, list, tuple, bytes],
    ) -> 'TableCreateRequest':
        return super().__new__(
            cls,
            *_args,
            columns=columns,
            name=name,
            comment=comment,
            properties=properties,
            sortOrders=sortOrders,
            distribution=distribution,
            partitioning=partitioning,
            indexes=indexes,
            _configuration=_configuration,
            **kwargs,
        )

from pygravitino.model.column import Column
from pygravitino.model.distribution import Distribution
from pygravitino.model.index_spec import IndexSpec
from pygravitino.model.partitioning_spec import PartitioningSpec
from pygravitino.model.sort_order import SortOrder
