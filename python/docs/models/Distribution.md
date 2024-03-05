# pygravitino.model.distribution.Distribution

Describes how data is distributed across partitions

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict, None,  | frozendict.frozendict, NoneClass,  | Describes how data is distributed across partitions | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**number** | decimal.Decimal, int,  | decimal.Decimal,  | The number of buckets/distribution | value must be a 32 bit integer
**[funcArgs](#funcArgs)** | list, tuple, None,  | tuple, NoneClass,  | The arguments of the distribution function | 
**strategy** | None, str,  | NoneClass, str,  | The distribution strategy | [optional] must be one of ["hash", "range", "even", ] if omitted the server will use the default value of "hash"
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# funcArgs

The arguments of the distribution function

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple, None,  | tuple, NoneClass,  | The arguments of the distribution function | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**FunctionArg**](FunctionArg.md) | [**FunctionArg**](FunctionArg.md) | [**FunctionArg**](FunctionArg.md) |  | 

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

