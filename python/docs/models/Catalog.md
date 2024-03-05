# pygravitino.model.catalog.Catalog

A catalog

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | A catalog | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**provider** | str,  | str,  | The provider of the catalog | must be one of ["hive", "lakehouse-iceberg", "jdbc-mysql", "jdbc-postgresql", ] 
**audit** | [**Audit**](Audit.md) | [**Audit**](Audit.md) |  | 
**name** | str,  | str,  | The name of the catalog | 
**type** | str,  | str,  | The type of the catalog | must be one of ["relational", "file", "stream", ] 
**comment** | None, str,  | NoneClass, str,  | A comment about the catalog | [optional] 
**[properties](#properties)** | dict, frozendict.frozendict,  | frozendict.frozendict,  | Configured string to string map of properties for the catalog | [optional] if omitted the server will use the default value of {}
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# properties

Configured string to string map of properties for the catalog

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | Configured string to string map of properties for the catalog | if omitted the server will use the default value of {}

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**any_string_name** | str,  | str,  | any string name can be used but the value must be the correct type | [optional] 

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

