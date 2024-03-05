# pygravitino.model.table.Table

A table object

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  | A table object | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**audit** | [**Audit**](Audit.md) | [**Audit**](Audit.md) |  | 
**[columns](#columns)** | list, tuple,  | tuple,  | A list of columns | 
**name** | str,  | str,  | The name of the table | 
**comment** | None, str,  | NoneClass, str,  | The comment of the table | [optional] 
**[properties](#properties)** | dict, frozendict.frozendict, None,  | frozendict.frozendict, NoneClass,  | A map of table properties | [optional] if omitted the server will use the default value of {}
**distribution** | [**Distribution**](Distribution.md) | [**Distribution**](Distribution.md) |  | [optional] 
**[sortOrders](#sortOrders)** | list, tuple, None,  | tuple, NoneClass,  | Describes the sort order of the table data | [optional] 
**[partitioning](#partitioning)** | list, tuple, None,  | tuple, NoneClass,  | partitioning of the table data | [optional] 
**[indexes](#indexes)** | list, tuple, None,  | tuple, NoneClass,  | Indexes of the table | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# columns

A list of columns

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple,  | tuple,  | A list of columns | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**Column**](Column.md) | [**Column**](Column.md) | [**Column**](Column.md) |  | 

# properties

A map of table properties

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict, None,  | frozendict.frozendict, NoneClass,  | A map of table properties | if omitted the server will use the default value of {}

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**any_string_name** | str,  | str,  | any string name can be used but the value must be the correct type | [optional] 

# sortOrders

Describes the sort order of the table data

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple, None,  | tuple, NoneClass,  | Describes the sort order of the table data | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**SortOrder**](SortOrder.md) | [**SortOrder**](SortOrder.md) | [**SortOrder**](SortOrder.md) |  | 

# partitioning

partitioning of the table data

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple, None,  | tuple, NoneClass,  | partitioning of the table data | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**PartitioningSpec**](PartitioningSpec.md) | [**PartitioningSpec**](PartitioningSpec.md) | [**PartitioningSpec**](PartitioningSpec.md) |  | 

# indexes

Indexes of the table

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple, None,  | tuple, NoneClass,  | Indexes of the table | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**IndexSpec**](IndexSpec.md) | [**IndexSpec**](IndexSpec.md) | [**IndexSpec**](IndexSpec.md) |  | 

[[Back to Model list]](../../README.md#documentation-for-models) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to README]](../../README.md)

