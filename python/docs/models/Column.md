# pygravitino.model.column.Column

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**name** | str,  | str,  | The name of the column | 
**type** | [**DataType**](DataType.md) | [**DataType**](DataType.md) |  | 
**comment** | None, str,  | NoneClass, str,  | The comment of the column | [optional] 
**nullable** | None, bool,  | NoneClass, BoolClass,  | Whether the column is nullable | [optional] if omitted the server will use the default value of True
**defaultValue** | [**FunctionArg**](FunctionArg.md) | [**FunctionArg**](FunctionArg.md) |  | [optional] 
**autoIncrement** | None, bool,  | NoneClass, BoolClass,  | Whether the column is auto increment | [optional] if omitted the server will use the default value of False
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

