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


class IdentityPartitioning(
    schemas.DictSchema
):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """


    class MetaOapg:
        required = {
            "fieldName",
            "strategy",
        }
        
        class properties:
            
            
            class strategy(
                schemas.EnumBase,
                schemas.StrSchema
            ):
            
            
                class MetaOapg:
                    enum_value_to_name = {
                        "identity": "IDENTITY",
                    }
                
                @schemas.classproperty
                def IDENTITY(cls):
                    return cls("identity")
        
            @staticmethod
            def fieldName() -> typing.Type['FieldName']:
                return FieldName
            __annotations__ = {
                "strategy": strategy,
                "fieldName": fieldName,
            }
    
    fieldName: 'FieldName'
    strategy: MetaOapg.properties.strategy
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["strategy"]) -> MetaOapg.properties.strategy: ...
    
    @typing.overload
    def __getitem__(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def __getitem__(self, name: str) -> schemas.UnsetAnyTypeSchema: ...
    
    def __getitem__(self, name: typing.Union[typing_extensions.Literal["strategy", "fieldName", ], str]):
        # dict_instance[name] accessor
        return super().__getitem__(name)
    
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["strategy"]) -> MetaOapg.properties.strategy: ...
    
    @typing.overload
    def get_item_oapg(self, name: typing_extensions.Literal["fieldName"]) -> 'FieldName': ...
    
    @typing.overload
    def get_item_oapg(self, name: str) -> typing.Union[schemas.UnsetAnyTypeSchema, schemas.Unset]: ...
    
    def get_item_oapg(self, name: typing.Union[typing_extensions.Literal["strategy", "fieldName", ], str]):
        return super().get_item_oapg(name)
    

    def __new__(
        cls,
        *_args: typing.Union[dict, frozendict.frozendict, ],
        fieldName: 'FieldName',
        strategy: typing.Union[MetaOapg.properties.strategy, str, ],
        _configuration: typing.Optional[schemas.Configuration] = None,
        **kwargs: typing.Union[schemas.AnyTypeSchema, dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, None, list, tuple, bytes],
    ) -> 'IdentityPartitioning':
        return super().__new__(
            cls,
            *_args,
            fieldName=fieldName,
            strategy=strategy,
            _configuration=_configuration,
            **kwargs,
        )

from pygravitino.model.field_name import FieldName
