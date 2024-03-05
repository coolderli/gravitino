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


class Column(
    schemas.DictSchema
):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """


    class MetaOapg:
        required = {
            "name",
            "type",
        }
        
        class properties:
            name = schemas.StrSchema
        
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
        
            @staticmethod
            def defaultValue() -> typing.Type['FunctionArg']:
                return FunctionArg
            
            
            class autoIncrement(
                schemas.BoolBase,
                schemas.NoneBase,
                schemas.Schema,
                schemas.NoneBoolMixin
            ):
            
            
                def __new__(
                    cls,
                    *_args: typing.Union[None, bool, ],
                    _configuration: typing.Optional[schemas.Configuration] = None,
                ) -> 'autoIncrement':
                    return super().__new__(
                        cls,
                        *_args,
                        _configuration=_configuration,
                    )
            __annotations__ = {
                "name": name,
                "type": type,
                "comment": comment,
                "nullable": nullable,
                "defaultValue": defaultValue,
                "autoIncrement": autoIncrement,
            }
    
    name: MetaOapg.properties.name
    type: 'DataType'
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["name"]) -> MetaOapg.properties.name: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["type"]) -> 'DataType': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["comment"]) -> MetaOapg.properties.comment: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["nullable"]) -> MetaOapg.properties.nullable: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["defaultValue"]) -> 'FunctionArg': ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["autoIncrement"]) -> MetaOapg.properties.autoIncrement: ...
    
    @typing.overload
    def __getitem__(self, name: str) -> schemas.UnsetAnyTypeSchema: ...
    
    def __getitem__(self, name: typing.Union[typing_extensions.Literal["name", "type", "comment", "nullable", "defaultValue", "autoIncrement", ], str]):
        # dict_instance[name] accessor
        return super().__getitem__(name)
    
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["name"]) -> MetaOapg.properties.name: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["type"]) -> 'DataType': ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["comment"]) -> typing.Union[MetaOapg.properties.comment, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["nullable"]) -> typing.Union[MetaOapg.properties.nullable, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["defaultValue"]) -> typing.Union['FunctionArg', schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["autoIncrement"]) -> typing.Union[MetaOapg.properties.autoIncrement, schemas.Unset]: ...
    
    @typing.overload
    def get_item_oapg(self, name: str) -> typing.Union[schemas.UnsetAnyTypeSchema, schemas.Unset]: ...
    
    def get_item_oapg(self, name: typing.Union[typing_extensions.Literal["name", "type", "comment", "nullable", "defaultValue", "autoIncrement", ], str]):
        return super().get_item_oapg(name)
    

    def __new__(
        cls,
        *_args: typing.Union[dict, frozendict.frozendict, ],
        name: typing.Union[MetaOapg.properties.name, str, ],
        type: 'DataType',
        comment: typing.Union[MetaOapg.properties.comment, None, str, schemas.Unset] = schemas.unset,
        nullable: typing.Union[MetaOapg.properties.nullable, None, bool, schemas.Unset] = schemas.unset,
        defaultValue: typing.Union['FunctionArg', schemas.Unset] = schemas.unset,
        autoIncrement: typing.Union[MetaOapg.properties.autoIncrement, None, bool, schemas.Unset] = schemas.unset,
        _configuration: typing.Optional[schemas.Configuration] = None,
        **kwargs: typing.Union[schemas.AnyTypeSchema, dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, None, list, tuple, bytes],
    ) -> 'Column':
        return super().__new__(
            cls,
            *_args,
            name=name,
            type=type,
            comment=comment,
            nullable=nullable,
            defaultValue=defaultValue,
            autoIncrement=autoIncrement,
            _configuration=_configuration,
            **kwargs,
        )

from pygravitino.model.data_type import DataType
from pygravitino.model.function_arg import FunctionArg
