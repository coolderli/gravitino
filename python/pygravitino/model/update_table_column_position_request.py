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


class UpdateTableColumnPositionRequest(
    schemas.DictSchema
):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """


    class MetaOapg:
        required = {
            "newPosition",
            "fieldName",
            "@type",
        }
        
        class properties:
            
            
            class type(
                schemas.EnumBase,
                schemas.StrSchema
            ):
            
            
                class MetaOapg:
                    enum_value_to_name = {
                        "updateColumnPosition": "UPDATE_COLUMN_POSITION",
                    }
                
                @schemas.classproperty
                def UPDATE_COLUMN_POSITION(cls):
                    return cls("updateColumnPosition")
        
            @staticmethod
            def fieldName() -> typing.Type['FieldName']:
                return FieldName
        
            @staticmethod
            def newPosition() -> typing.Type['ColumnPosition']:
                return ColumnPosition
            __annotations__ = {
                "@type": type,
                "fieldName": fieldName,
                "newPosition": newPosition,
            }
    
    newPosition: 'ColumnPosition'
    fieldName: 'FieldName'
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["@type"]) -> MetaOapg.properties.type: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["newPosition"]) -> 'ColumnPosition': ...
    
    @typing.overload
    def __getitem__(self, name: str) -> schemas.UnsetAnyTypeSchema: ...
    
    def __getitem__(self, name: typing.Union[typing_extensions.Literal["@type", "fieldName", "newPosition", ], str]):
        # dict_instance[name] accessor
        return super().__getitem__(name)
    
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["@type"]) -> MetaOapg.properties.type: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["newPosition"]) -> 'ColumnPosition': ...
    
    @typing.overload
    def get_item_oapg(self, name: str) -> typing.Union[schemas.UnsetAnyTypeSchema, schemas.Unset]: ...
    
    def get_item_oapg(self, name: typing.Union[typing_extensions.Literal["@type", "fieldName", "newPosition", ], str]):
        return super().get_item_oapg(name)
    

    def __new__(
        cls,
        *_args: typing.Union[dict, frozendict.frozendict, ],
        newPosition: 'ColumnPosition',
        fieldName: 'FieldName',
        _configuration: typing.Optional[schemas.Configuration] = None,
        **kwargs: typing.Union[schemas.AnyTypeSchema, dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, None, list, tuple, bytes],
    ) -> 'UpdateTableColumnPositionRequest':
        return super().__new__(
            cls,
            *_args,
            newPosition=newPosition,
            fieldName=fieldName,
            _configuration=_configuration,
            **kwargs,
        )

from pygravitino.model.column_position import ColumnPosition
from pygravitino.model.field_name import FieldName
