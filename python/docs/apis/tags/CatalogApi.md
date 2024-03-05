<a id="__pageTop"></a>
# pygravitino.apis.tags.catalog_api.CatalogApi

All URIs are relative to *http://localhost:8090/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_catalog**](#alter_catalog) | **put** /metalakes/{metalake}/catalogs/{catalog} | Update catalog
[**create_catalog**](#create_catalog) | **post** /metalakes/{metalake}/catalogs | Create catalog
[**drop_catalog**](#drop_catalog) | **delete** /metalakes/{metalake}/catalogs/{catalog} | Drop catalog
[**list_catalogs**](#list_catalogs) | **get** /metalakes/{metalake}/catalogs | List catalogs
[**load_catalog**](#load_catalog) | **get** /metalakes/{metalake}/catalogs/{catalog} | Get catalog

# **alter_catalog**
<a id="alter_catalog"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} alter_catalog(metalakecatalog)

Update catalog

Alters the specified catalog information in the specified metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import catalog_api
from pygravitino.model.catalog import Catalog
from pygravitino.model.catalog_updates_request import CatalogUpdatesRequest
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
    api_instance = catalog_api.CatalogApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    try:
        # Update catalog
        api_response = api_instance.alter_catalog(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->alter_catalog: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    body = CatalogUpdatesRequest(
        updates=[
            CatalogUpdateRequest(
                type="RemoveCatalogPropertyRequest",
                _property="_property_example",
            )
        ],
    )
    try:
        # Update catalog
        api_response = api_instance.alter_catalog(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->alter_catalog: %s\n" % e)
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
[**CatalogUpdatesRequest**](../../models/CatalogUpdatesRequest.md) |  | 


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
200 | [ApiResponseFor200](#alter_catalog.ApiResponseFor200) | Returns included catalog object
404 | [ApiResponseFor404](#alter_catalog.ApiResponseFor404) | Not Found - The specified catalog does not exist in the specified metalake
409 | [ApiResponseFor409](#alter_catalog.ApiResponseFor409) | Conflict - The target catalog already exists in the specified metalake
5xx | [ApiResponseFor5xx](#alter_catalog.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### alter_catalog.ApiResponseFor200
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
**catalog** | [**Catalog**]({{complexTypePrefix}}Catalog.md) | [**Catalog**]({{complexTypePrefix}}Catalog.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### alter_catalog.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_catalog.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### alter_catalog.ApiResponseFor5xx
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

# **create_catalog**
<a id="create_catalog"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} create_catalog(metalake)

Create catalog

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import catalog_api
from pygravitino.model.catalog_create_request import CatalogCreateRequest
from pygravitino.model.catalog import Catalog
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
    api_instance = catalog_api.CatalogApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
    }
    try:
        # Create catalog
        api_response = api_instance.create_catalog(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->create_catalog: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
    }
    body = CatalogCreateRequest(
        name="name_example",
        type="relational",
        provider="hive",
        comment="comment_example",
        properties=dict(
            "key": "key_example",
        ),
    )
    try:
        # Create catalog
        api_response = api_instance.create_catalog(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->create_catalog: %s\n" % e)
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
[**CatalogCreateRequest**](../../models/CatalogCreateRequest.md) |  | 


### path_params
#### RequestPathParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
metalake | MetalakeSchema | | 

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#create_catalog.ApiResponseFor200) | Returns included catalog object
409 | [ApiResponseFor409](#create_catalog.ApiResponseFor409) | Conflict - The target catalog already exists in the specified metalake
5xx | [ApiResponseFor5xx](#create_catalog.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### create_catalog.ApiResponseFor200
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
**catalog** | [**Catalog**]({{complexTypePrefix}}Catalog.md) | [**Catalog**]({{complexTypePrefix}}Catalog.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### create_catalog.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### create_catalog.ApiResponseFor5xx
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

# **drop_catalog**
<a id="drop_catalog"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} drop_catalog(metalakecatalog)

Drop catalog

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import catalog_api
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
    api_instance = catalog_api.CatalogApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    try:
        # Drop catalog
        api_response = api_instance.drop_catalog(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->drop_catalog: %s\n" % e)
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
200 | [ApiResponseFor200](#drop_catalog.ApiResponseFor200) | Represents a response for a drop operation
400 | [ApiResponseFor400](#drop_catalog.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#drop_catalog.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### drop_catalog.ApiResponseFor200
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

#### drop_catalog.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### drop_catalog.ApiResponseFor5xx
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

# **list_catalogs**
<a id="list_catalogs"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} list_catalogs(metalake)

List catalogs

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import catalog_api
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
    api_instance = catalog_api.CatalogApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
    }
    try:
        # List catalogs
        api_response = api_instance.list_catalogs(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->list_catalogs: %s\n" % e)
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

# MetalakeSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#list_catalogs.ApiResponseFor200) | A list of entities
400 | [ApiResponseFor400](#list_catalogs.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#list_catalogs.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### list_catalogs.ApiResponseFor200
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

#### list_catalogs.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### list_catalogs.ApiResponseFor5xx
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

# **load_catalog**
<a id="load_catalog"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} load_catalog(metalakecatalog)

Get catalog

Returns the specified catalog information in the specified metalake

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import catalog_api
from pygravitino.model.catalog import Catalog
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
    api_instance = catalog_api.CatalogApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
    }
    try:
        # Get catalog
        api_response = api_instance.load_catalog(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling CatalogApi->load_catalog: %s\n" % e)
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
200 | [ApiResponseFor200](#load_catalog.ApiResponseFor200) | Returns included catalog object
404 | [ApiResponseFor404](#load_catalog.ApiResponseFor404) | Not Found - The specified catalog does not exist in the specified metalake
5xx | [ApiResponseFor5xx](#load_catalog.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### load_catalog.ApiResponseFor200
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
**catalog** | [**Catalog**]({{complexTypePrefix}}Catalog.md) | [**Catalog**]({{complexTypePrefix}}Catalog.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### load_catalog.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### load_catalog.ApiResponseFor5xx
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

