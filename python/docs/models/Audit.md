# pygravitino.model.audit.Audit

Audit information for a Gravitino resource.

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | Audit information for a Gravitino resource. | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**creator** | str,  | str,  | The user who created the resource | [optional] 
**createTime** | str, datetime,  | str,  | The time the resource was created | [optional] value must conform to RFC-3339 date-time
**lastModifier** | str,  | str,  | The user who last modified the resource | [optional] 
**lastModifiedTime** | str, datetime,  | str,  | The time the resource was last modified | [optional] value must conform to RFC-3339 date-time
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

