<a id="__pageTop"></a>
# pygravitino.apis.tags.schema_api.SchemaApi

All URIs are relative to *http://localhost:8090/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_schema**](#alter_schema) | **put** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema} | Update schema
[**create_schema**](#create_schema) | **post** /metalakes/{metalake}/catalogs/{catalog}/schemas | Create schema
[**drop_schema**](#drop_schema) | **delete** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema} | Drop schema
[**list_schemas**](#list_schemas) | **get** /metalakes/{metalake}/catalogs/{catalog}/schemas | List schemas
[**load_schema**](#load_schema) | **get** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema} | Get schema

# **alter_schema**
<a id="alter_schema"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} alter_schema(metalakecatalogschema)

Update schema

Updates the specified schema in the specified catalog and metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import schema_api
from pygravitino.model.schema import Schema
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.schema_updates_request import SchemaUpdatesRequest
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
    api_instance = schema_api.SchemaApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    try:
        # Update schema
        api_response = api_instance.alter_schema(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->alter_schema: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    body = SchemaUpdatesRequest(
        updates=[
            SchemaUpdateRequest(
                type="RemoveSchemaPropertyRequest",
                _property="_property_example",
            )
        ],
    )
    try:
        # Update schema
        api_response = api_instance.alter_schema(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->alter_schema: %s\n" % e)
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
[**SchemaUpdatesRequest**](../../models/SchemaUpdatesRequest.md) |  | 


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
200 | [ApiResponseFor200](#alter_schema.ApiResponseFor200) | Returns include the schema object
404 | [ApiResponseFor404](#alter_schema.ApiResponseFor404) | Not Found - The target schema does not exist
409 | [ApiResponseFor409](#alter_schema.ApiResponseFor409) | Conflict - The target schema already exists
5xx | [ApiResponseFor5xx](#alter_schema.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### alter_schema.ApiResponseFor200
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
**schema** | [**Schema**]({{complexTypePrefix}}Schema.md) | [**Schema**]({{complexTypePrefix}}Schema.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### alter_schema.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_schema.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_schema.ApiResponseFor5xx
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

# **create_schema**
<a id="create_schema"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} create_schema(metalakecatalog)

Create schema

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import schema_api
from pygravitino.model.schema_create_request import SchemaCreateRequest
from pygravitino.model.schema import Schema
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
    api_instance = schema_api.SchemaApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    try:
        # Create schema
        api_response = api_instance.create_schema(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->create_schema: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    body = SchemaCreateRequest(
        name="name_example",
        comment="comment_example",
        properties=dict(
            "key": "key_example",
        ),
    )
    try:
        # Create schema
        api_response = api_instance.create_schema(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->create_schema: %s\n" % e)
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
[**SchemaCreateRequest**](../../models/SchemaCreateRequest.md) |  | 


### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 
catalog | CatalogSchema | | 

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

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#create_schema.ApiResponseFor200) | Returns include the schema object
409 | [ApiResponseFor409](#create_schema.ApiResponseFor409) | Conflict - The target schema already exists
5xx | [ApiResponseFor5xx](#create_schema.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### create_schema.ApiResponseFor200
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
**schema** | [**Schema**]({{complexTypePrefix}}Schema.md) | [**Schema**]({{complexTypePrefix}}Schema.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### create_schema.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### create_schema.ApiResponseFor5xx
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

# **drop_schema**
<a id="drop_schema"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} drop_schema(metalakecatalogschema)

Drop schema

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import schema_api
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
    api_instance = schema_api.SchemaApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    try:
        # Drop schema
        api_response = api_instance.drop_schema(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->drop_schema: %s\n" % e)
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
200 | [ApiResponseFor200](#drop_schema.ApiResponseFor200) | Represents a response for a drop operation
400 | [ApiResponseFor400](#drop_schema.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#drop_schema.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### drop_schema.ApiResponseFor200
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

#### drop_schema.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### drop_schema.ApiResponseFor5xx
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

# **list_schemas**
<a id="list_schemas"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} list_schemas(metalakecatalog)

List schemas

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import schema_api
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
    api_instance = schema_api.SchemaApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    try:
        # List schemas
        api_response = api_instance.list_schemas(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->list_schemas: %s\n" % e)
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

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#list_schemas.ApiResponseFor200) | A list of entities
400 | [ApiResponseFor400](#list_schemas.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#list_schemas.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### list_schemas.ApiResponseFor200
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

#### list_schemas.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### list_schemas.ApiResponseFor5xx
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

# **load_schema**
<a id="load_schema"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} load_schema(metalakecatalogschema)

Get schema

Returns the specified schema in the specified catalog and metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import schema_api
from pygravitino.model.schema import Schema
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
    api_instance = schema_api.SchemaApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
    }
    try:
        # Get schema
        api_response = api_instance.load_schema(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling SchemaApi->load_schema: %s\n" % e)
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
200 | [ApiResponseFor200](#load_schema.ApiResponseFor200) | Returns include the schema object
404 | [ApiResponseFor404](#load_schema.ApiResponseFor404) | Not Found - The target schema does not exist
5xx | [ApiResponseFor5xx](#load_schema.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### load_schema.ApiResponseFor200
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
**schema** | [**Schema**]({{complexTypePrefix}}Schema.md) | [**Schema**]({{complexTypePrefix}}Schema.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### load_schema.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### load_schema.ApiResponseFor5xx
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

