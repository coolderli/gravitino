# pygravitino.model.metalake.Metalake

Gravitino Metalake is the top-level metadata repository for users. It contains a list of catalogs as sub-level metadata collections. With GravitinoMetaLake, users can list, create, load, alter and drop a catalog with specified identifier.

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | Gravitino Metalake is the top-level metadata repository for users. It contains a list of catalogs as sub-level metadata collections. With GravitinoMetaLake, users can list, create, load, alter and drop a catalog with specified identifier. | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**name** | str,  | str,  | The name of the Metalake | 
**comment** | None, str,  | NoneClass, str,  | A comment about the Metalake | [optional] 
**audit** | [**Audit**](Audit.md) | [**Audit**](Audit.md) |  | [optional] 
**[properties](#properties)** | dict, frozendict.frozendict,  | frozendict.frozendict,  | Configured string to string map of properties for the Metalake | [optional] if omitted the server will use the default value of {}
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# properties

Configured string to string map of properties for the Metalake

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | Configured string to string map of properties for the Metalake | if omitted the server will use the default value of {}

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**any_string_name** | str,  | str,  | any string name can be used but the value must be the correct type | [optional] 

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

