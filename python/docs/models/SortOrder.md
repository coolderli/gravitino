# pygravitino.model.sort_order.SortOrder

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**sortTerm** | [**FunctionArg**](FunctionArg.md) | [**FunctionArg**](FunctionArg.md) |  | 
**direction** | None, str,  | NoneClass, str,  | The direction of the sort order | [optional] must be one of ["asc", "desc", ] if omitted the server will use the default value of "asc"
**nullOrdering** | str,  | str,  | ｜ The sort order of null values. The default value is \&quot;nulls_first\&quot; if the direction is \&quot;asc\&quot;, otherwise \&quot;nulls_last\&quot;. | [optional] must be one of ["nulls_first", "nulls_last", ] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

