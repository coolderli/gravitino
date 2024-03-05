<a id="__pageTop"></a>
# pygravitino.apis.tags.metalake_api.MetalakeApi

All URIs are relative to *http://localhost:8090/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_metalake**](#alter_metalake) | **put** /metalakes/{name} | Update metalake
[**create_metalake**](#create_metalake) | **post** /metalakes | Create metalake
[**drop_metalake**](#drop_metalake) | **delete** /metalakes/{name} | Drop metalake
[**list_metalakes**](#list_metalakes) | **get** /metalakes | List metalakes
[**load_metalake**](#load_metalake) | **get** /metalakes/{name} | Get metalake

# **alter_metalake**
<a id="alter_metalake"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} alter_metalake(name)

Update metalake

Alters a specified metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import metalake_api
from pygravitino.model.metalake_updates_request import MetalakeUpdatesRequest
from pygravitino.model.metalake import Metalake
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
    api_instance = metalake_api.MetalakeApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'name': "name_example",
    }
    try:
        # Update metalake
        api_response = api_instance.alter_metalake(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->alter_metalake: %s\n" % e)

    # example passing only optional values
    path_params = {
        'name': "name_example",
    }
    body = MetalakeUpdatesRequest(
        updates=[
            MetalakeUpdateRequest(
                type="RemoveMetalakePropertyRequest",
                _property="_property_example",
            )
        ],
    )
    try:
        # Update metalake
        api_response = api_instance.alter_metalake(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->alter_metalake: %s\n" % e)
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
[**MetalakeUpdatesRequest**](../../models/MetalakeUpdatesRequest.md) |  | 


### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
name | NameSchema | | 

# NameSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#alter_metalake.ApiResponseFor200) | Returns included metalake object.
404 | [ApiResponseFor404](#alter_metalake.ApiResponseFor404) | Not Found - The metalake does not exist
409 | [ApiResponseFor409](#alter_metalake.ApiResponseFor409) | Conflict - The target (metalake)name already exists
500 | [ApiResponseFor500](#alter_metalake.ApiResponseFor500) | Internal server error. It is possible that the server encountered a storage issue.

#### alter_metalake.ApiResponseFor200
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
**metalake** | [**Metalake**]({{complexTypePrefix}}Metalake.md) | [**Metalake**]({{complexTypePrefix}}Metalake.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### alter_metalake.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_metalake.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_metalake.ApiResponseFor500
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor500ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor500ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


### Authorization

[BasicAuth](../../../README.md#BasicAuth), [OAuth2WithJWT](../../../README.md#OAuth2WithJWT)

[[Back to top]](#__pageTop) [[Back to API list]](../../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../../README.md#documentation-for-models) [[Back to README]](../../../README.md)

# **create_metalake**
<a id="create_metalake"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} create_metalake()

Create metalake

Creates a new metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import metalake_api
from pygravitino.model.metalake import Metalake
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.metalake_create_request import MetalakeCreateRequest
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
    api_instance = metalake_api.MetalakeApi(api_client)

    # example passing only optional values
    body = MetalakeCreateRequest(
        name="name_example",
        comment="comment_example",
        properties=dict(
            "key": "key_example",
        ),
    )
    try:
        # Create metalake
        api_response = api_instance.create_metalake(
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->create_metalake: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
body | typing.Union[SchemaForRequestBodyApplicationJson, Unset] | optional, default is unset |
content_type | str | optional, default is 'application/json' | Selects the schema and serialization of the request body
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### body

# SchemaForRequestBodyApplicationJson
Type | Description  | Notes
------------- | ------------- | -------------
[**MetalakeCreateRequest**](../../models/MetalakeCreateRequest.md) |  | 


### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#create_metalake.ApiResponseFor200) | Returns included metalake object.
409 | [ApiResponseFor409](#create_metalake.ApiResponseFor409) | Conflict - The target metalake already exists
5xx | [ApiResponseFor5xx](#create_metalake.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### create_metalake.ApiResponseFor200
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
**metalake** | [**Metalake**]({{complexTypePrefix}}Metalake.md) | [**Metalake**]({{complexTypePrefix}}Metalake.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### create_metalake.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### create_metalake.ApiResponseFor5xx
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

# **drop_metalake**
<a id="drop_metalake"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} drop_metalake(name)

Drop metalake

Drops a specified metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import metalake_api
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
    api_instance = metalake_api.MetalakeApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'name': "name_example",
    }
    try:
        # Drop metalake
        api_response = api_instance.drop_metalake(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->drop_metalake: %s\n" % e)
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
name | NameSchema | | 

# NameSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#drop_metalake.ApiResponseFor200) | Represents a response for a drop operation
400 | [ApiResponseFor400](#drop_metalake.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#drop_metalake.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### drop_metalake.ApiResponseFor200
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

#### drop_metalake.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### drop_metalake.ApiResponseFor5xx
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

# **list_metalakes**
<a id="list_metalakes"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} list_metalakes()

List metalakes

Returns a list of all metalakes.

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import metalake_api
from pygravitino.model.metalake import Metalake
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
    api_instance = metalake_api.MetalakeApi(api_client)

    # example, this endpoint has no required or optional parameters
    try:
        # List metalakes
        api_response = api_instance.list_metalakes()
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->list_metalakes: %s\n" % e)
```
### Parameters
This endpoint does not need any parameter.

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#list_metalakes.ApiResponseFor200) | Returns a list of all metalakes.
400 | [ApiResponseFor400](#list_metalakes.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#list_metalakes.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### list_metalakes.ApiResponseFor200
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
**[metalakes](#metalakes)** | list, tuple,  | tuple,  | A list of metalake objects | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

# metalakes

A list of metalake objects

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
list, tuple,  | tuple,  | A list of metalake objects | 

### Tuple Items
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[**Metalake**]({{complexTypePrefix}}Metalake.md) | [**Metalake**]({{complexTypePrefix}}Metalake.md) | [**Metalake**]({{complexTypePrefix}}Metalake.md) |  | 

#### list_metalakes.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### list_metalakes.ApiResponseFor5xx
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

# **load_metalake**
<a id="load_metalake"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} load_metalake(name)

Get metalake

Returns a metalake information by given metalake name

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import metalake_api
from pygravitino.model.metalake import Metalake
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
    api_instance = metalake_api.MetalakeApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'name': "name_example",
    }
    try:
        # Get metalake
        api_response = api_instance.load_metalake(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling MetalakeApi->load_metalake: %s\n" % e)
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
name | NameSchema | | 

# NameSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#load_metalake.ApiResponseFor200) | Returns included metalake object.
404 | [ApiResponseFor404](#load_metalake.ApiResponseFor404) | Not Found - The metalake does not exist
5xx | [ApiResponseFor5xx](#load_metalake.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### load_metalake.ApiResponseFor200
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
**metalake** | [**Metalake**]({{complexTypePrefix}}Metalake.md) | [**Metalake**]({{complexTypePrefix}}Metalake.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### load_metalake.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### load_metalake.ApiResponseFor5xx
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

