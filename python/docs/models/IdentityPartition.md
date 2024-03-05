# pygravitino.model.identity_partition.IdentityPartition

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**fieldNames** | [**FieldNames**](FieldNames.md) | [**FieldNames**](FieldNames.md) |  | 
**[values](#values)** | list, tuple,  | tuple,  | The values of the partition, must be the same length and order as fieldNames | 
**type** | str,  | str,  |  | must be one of ["identity", ] 
**name** | str,  | str,  | The name of the partition | [optional] 
**properties** | [**Properties**](Properties.md) | [**Properties**](Properties.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# values

The values of the partition, must be the same length and order as fieldNames

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple,  | tuple,  | The values of the partition, must be the same length and order as fieldNames | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**Literal1**](Literal1.md) | [**Literal1**](Literal1.md) | [**Literal1**](Literal1.md) |  | 

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

