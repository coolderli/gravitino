<a id="__pageTop"></a>
# pygravitino.apis.tags.table_api.TableApi

All URIs are relative to *http://localhost:8090/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_table**](#alter_table) | **put** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table} | Update table
[**create_table**](#create_table) | **post** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables | Create table
[**drop_table**](#drop_table) | **delete** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table} | Drop table
[**list_tables**](#list_tables) | **get** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables | List tables
[**load_table**](#load_table) | **get** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table} | Get table

# **alter_table**
<a id="alter_table"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} alter_table(metalakecatalogschematable)

Update table

Updates the specified table in a schema

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import table_api
from pygravitino.model.table_updates_request import TableUpdatesRequest
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.table import Table
from pprint import pprint
# Defining the host is optional and defaults to http://localhost:8090/api
# See configuration.py for a list of all supported configuration parameters.
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: BasicAuth
configuration = pygravitino.Configuration(
    username = 'YOUR_USERNAME',
    password = 'YOUR_PASSWORD'
)

# Configure OAuth2 access token for authorization: OAuth2WithJWT
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api",
    access_token = 'YOUR_ACCESS_TOKEN'
)
# Enter a context with an instance of the API client
with pygravitino.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = table_api.TableApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    try:
        # Update table
        api_response = api_instance.alter_table(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->alter_table: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    body = TableUpdatesRequest(
        updates=[
            TableUpdateRequest(
                type="AddTableColumnRequest",
                field_name=FieldName([
                    "field_name_example"
                ]),
                type=DataType(None),
                comment="comment_example",
                position=ColumnPosition(None),
                nullable=True,
            )
        ],
    )
    try:
        # Update table
        api_response = api_instance.alter_table(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->alter_table: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
body | typing.Union[SchemaForRequestBodyApplicationJson, Unset] | optional, default is unset |
path_params | RequestPathParams | |
content_type | str | optional, default is 'application/json' | Selects the schema and serialization of the request body
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### body

# SchemaForRequestBodyApplicationJson
Type | Description  | Notes
------------- | ------------- | -------------
[**TableUpdatesRequest**](../../models/TableUpdatesRequest.md) |  | 


### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 
schema | SchemaSchema | | 
table | TableSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# CatalogSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# SchemaSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# TableSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#alter_table.ApiResponseFor200) | Returns include the table object
400 | [ApiResponseFor400](#alter_table.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
404 | [ApiResponseFor404](#alter_table.ApiResponseFor404) | Not Found - The target table does not exist
409 | [ApiResponseFor409](#alter_table.ApiResponseFor409) | Conflict - The target table already exists
5xx | [ApiResponseFor5xx](#alter_table.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### alter_table.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**code** | decimal.Decimal, int,  | decimal.Decimal,  | Status code of the response | [optional] must be one of [0, ] value must be a 32 bit integer
**table** | [**Table**]({{complexTypePrefix}}Table.md) | [**Table**]({{complexTypePrefix}}Table.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### alter_table.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_table.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_table.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_table.ApiResponseFor5xx
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor5xxResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor5xxResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

# **create_table**
<a id="create_table"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} create_table(metalakecatalogschema)

Create table

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import table_api
from pygravitino.model.table_create_request import TableCreateRequest
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.table import Table
from pprint import pprint
# Defining the host is optional and defaults to http://localhost:8090/api
# See configuration.py for a list of all supported configuration parameters.
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: BasicAuth
configuration = pygravitino.Configuration(
    username = 'YOUR_USERNAME',
    password = 'YOUR_PASSWORD'
)

# Configure OAuth2 access token for authorization: OAuth2WithJWT
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api",
    access_token = 'YOUR_ACCESS_TOKEN'
)
# Enter a context with an instance of the API client
with pygravitino.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = table_api.TableApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    try:
        # Create table
        api_response = api_instance.create_table(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->create_table: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    body = TableCreateRequest(
        name="name_example",
        columns=[
            Column(
                name="name_example",
                type=DataType(None),
                comment="comment_example",
                nullable=True,
                default_value=FunctionArg(
                    type="Field",
                    field_name=FieldName([
                        "field_name_example"
                    ]),
                ),
                auto_increment=False,
            )
        ],
        comment="comment_example",
        properties=dict(
            "key": "key_example",
        ),
        sort_orders=[],
        distribution=Distribution(
            strategy="hash",
            number=1,
            func_args=[],
        ),
        partitioning=[],
        indexes=[],
    )
    try:
        # Create table
        api_response = api_instance.create_table(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->create_table: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
body | typing.Union[SchemaForRequestBodyApplicationJson, Unset] | optional, default is unset |
path_params | RequestPathParams | |
content_type | str | optional, default is 'application/json' | Selects the schema and serialization of the request body
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### body

# SchemaForRequestBodyApplicationJson
Type | Description  | Notes
------------- | ------------- | -------------
[**TableCreateRequest**](../../models/TableCreateRequest.md) |  | 


### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 
schema | SchemaSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# CatalogSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# SchemaSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#create_table.ApiResponseFor200) | Returns include the table object
409 | [ApiResponseFor409](#create_table.ApiResponseFor409) | Conflict - The target table already exists
5xx | [ApiResponseFor5xx](#create_table.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### create_table.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**code** | decimal.Decimal, int,  | decimal.Decimal,  | Status code of the response | [optional] must be one of [0, ] value must be a 32 bit integer
**table** | [**Table**]({{complexTypePrefix}}Table.md) | [**Table**]({{complexTypePrefix}}Table.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### create_table.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### create_table.ApiResponseFor5xx
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor5xxResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor5xxResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

# **drop_table**
<a id="drop_table"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} drop_table(metalakecatalogschematable)

Drop table

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import table_api
from pygravitino.model.error_model import ErrorModel
from pprint import pprint
# Defining the host is optional and defaults to http://localhost:8090/api
# See configuration.py for a list of all supported configuration parameters.
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: BasicAuth
configuration = pygravitino.Configuration(
    username = 'YOUR_USERNAME',
    password = 'YOUR_PASSWORD'
)

# Configure OAuth2 access token for authorization: OAuth2WithJWT
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api",
    access_token = 'YOUR_ACCESS_TOKEN'
)
# Enter a context with an instance of the API client
with pygravitino.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = table_api.TableApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    try:
        # Drop table
        api_response = api_instance.drop_table(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->drop_table: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
path_params | RequestPathParams | |
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 
schema | SchemaSchema | | 
table | TableSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# CatalogSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# SchemaSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# TableSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#drop_table.ApiResponseFor200) | Represents a response for a drop operation
400 | [ApiResponseFor400](#drop_table.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#drop_table.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### drop_table.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**code** | decimal.Decimal, int,  | decimal.Decimal,  | Status code of the response | [optional] must be one of [0, ] value must be a 32 bit integer
**dropped** | bool,  | BoolClass,  | Whether the drop operation was successful | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### drop_table.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### drop_table.ApiResponseFor5xx
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor5xxResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor5xxResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

# **list_tables**
<a id="list_tables"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} list_tables(metalakecatalogschema)

List tables

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import table_api
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.name_identifier import NameIdentifier
from pprint import pprint
# Defining the host is optional and defaults to http://localhost:8090/api
# See configuration.py for a list of all supported configuration parameters.
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: BasicAuth
configuration = pygravitino.Configuration(
    username = 'YOUR_USERNAME',
    password = 'YOUR_PASSWORD'
)

# Configure OAuth2 access token for authorization: OAuth2WithJWT
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api",
    access_token = 'YOUR_ACCESS_TOKEN'
)
# Enter a context with an instance of the API client
with pygravitino.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = table_api.TableApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    try:
        # List tables
        api_response = api_instance.list_tables(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->list_tables: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
path_params | RequestPathParams | |
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 
schema | SchemaSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# CatalogSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# SchemaSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#list_tables.ApiResponseFor200) | A list of entities
400 | [ApiResponseFor400](#list_tables.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#list_tables.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### list_tables.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**code** | decimal.Decimal, int,  | decimal.Decimal,  | Status code of the response | [optional] must be one of [0, ] value must be a 32 bit integer
**[identifiers](#identifiers)** | list, tuple,  | tuple,  | A list of NameIdentifier objects | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# identifiers

A list of NameIdentifier objects

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple,  | tuple,  | A list of NameIdentifier objects | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**NameIdentifier**]({{complexTypePrefix}}NameIdentifier.md) | [**NameIdentifier**]({{complexTypePrefix}}NameIdentifier.md) | [**NameIdentifier**]({{complexTypePrefix}}NameIdentifier.md) |  | 

#### list_tables.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### list_tables.ApiResponseFor5xx
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor5xxResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor5xxResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

# **load_table**
<a id="load_table"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} load_table(metalakecatalogschematable)

Get table

Returns the specified table object

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import table_api
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.table import Table
from pprint import pprint
# Defining the host is optional and defaults to http://localhost:8090/api
# See configuration.py for a list of all supported configuration parameters.
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: BasicAuth
configuration = pygravitino.Configuration(
    username = 'YOUR_USERNAME',
    password = 'YOUR_PASSWORD'
)

# Configure OAuth2 access token for authorization: OAuth2WithJWT
configuration = pygravitino.Configuration(
    host = "http://localhost:8090/api",
    access_token = 'YOUR_ACCESS_TOKEN'
)
# Enter a context with an instance of the API client
with pygravitino.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = table_api.TableApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    try:
        # Get table
        api_response = api_instance.load_table(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling TableApi->load_table: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
path_params | RequestPathParams | |
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 
schema | SchemaSchema | | 
table | TableSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# CatalogSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# SchemaSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

# TableSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#load_table.ApiResponseFor200) | Returns include the table object
404 | [ApiResponseFor404](#load_table.ApiResponseFor404) | Not Found - The target table does not exist
5xx | [ApiResponseFor5xx](#load_table.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### load_table.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict,  | frozendict.frozendict,  |  | 

### Dictionary Keys
Key | Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | ------------- | -------------
**code** | decimal.Decimal, int,  | decimal.Decimal,  | Status code of the response | [optional] must be one of [0, ] value must be a 32 bit integer
**table** | [**Table**]({{complexTypePrefix}}Table.md) | [**Table**]({{complexTypePrefix}}Table.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### load_table.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### load_table.ApiResponseFor5xx
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor5xxResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor5xxResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

