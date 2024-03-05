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


class AddTableColumnRequest(
    schemas.DictSchema
):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """


    class MetaOapg:
        required = {
            "fieldName",
            "@type",
            "type",
        }
        
        class properties:
            
            
            class type(
                schemas.EnumBase,
                schemas.StrSchema
            ):
            
            
                class MetaOapg:
                    enum_value_to_name = {
                        "addColumn": "ADD_COLUMN",
                    }
                
                @schemas.classproperty
                def ADD_COLUMN(cls):
                    return cls("addColumn")
        
            @staticmethod
            def fieldName() -> typing.Type['FieldName']:
                return FieldName
        
            @staticmethod
            def type() -> typing.Type['DataType']:
                return DataType
            
            
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
        
            @staticmethod
            def position() -> typing.Type['ColumnPosition']:
                return ColumnPosition
            
            
            class nullable(
                schemas.BoolBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneBoolMixin
            ):
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[None, bool, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'nullable':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
            __annotations__ = {
                "@type": type,
                "fieldName": fieldName,
                "type": type,
                "comment": comment,
                "position": position,
                "nullable": nullable,
            }
    
    fieldName: 'FieldName'
    type: 'DataType'
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["@type"]) -> MetaOapg.properties.type: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["type"]) -> 'DataType': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["comment"]) -> MetaOapg.properties.comment: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["position"]) -> 'ColumnPosition': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["nullable"]) -> MetaOapg.properties.nullable: ...
    
    @typing.overload
    def __getitem__(self, name: str) -> schemas.UnsetAnyTypeSchema: ...
    
    def __getitem__(self, name: typing.Union[typing_extensions.Literal["@type", "fieldName", "type", "comment", "position", "nullable", ], str]):
        # dict_instance[name] accessor
        return super().__getitem__(name)
    
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["@type"]) -> MetaOapg.properties.type: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["type"]) -> 'DataType': ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["comment"]) -> typing.Union[MetaOapg.properties.comment, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["position"]) -> typing.Union['ColumnPosition', schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["nullable"]) -> typing.Union[MetaOapg.properties.nullable, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: str) -> typing.Union[schemas.UnsetAnyTypeSchema, schemas.Unset]: ...
    
    def get_item_oapg(self, name: typing.Union[typing_extensions.Literal["@type", "fieldName", "type", "comment", "position", "nullable", ], str]):
        return super().get_item_oapg(name)
    

    def __new__(
        cls,
        *_args: typing.Union[dict, frozendict.frozendict, ],
        fieldName: 'FieldName',
        type: 'DataType',
        comment: typing.Union[MetaOapg.properties.comment, None, str, schemas.Unset] = schemas.unset,
        position: typing.Union['ColumnPosition', schemas.Unset] = schemas.unset,
        nullable: typing.Union[MetaOapg.properties.nullable, None, bool, schemas.Unset] = schemas.unset,
        _configuration: typing.Optional[schemas.Configuration] = None,
        **kwargs: typing.Union[schemas.AnyTypeSchema, dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, None, list, tuple, bytes],
    ) -> 'AddTableColumnRequest':
        return super().__new__(
            cls,
            *_args,
            fieldName=fieldName,
            type=type,
            comment=comment,
            position=position,
            nullable=nullable,
            _configuration=_configuration,
            **kwargs,
        )

from pygravitino.model.column_position import ColumnPosition
from pygravitino.model.data_type import DataType
from pygravitino.model.field_name import FieldName
