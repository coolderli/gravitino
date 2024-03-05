<a id="__pageTop"></a>
# pygravitino.apis.tags.partition_api.PartitionApi

All URIs are relative to *http://localhost:8090/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**add_partitions**](#add_partitions) | **post** /metalaskes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}/partitions | Add partitions
[**get_partition**](#get_partition) | **get** /metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}/partitions/{partition} | Get partition by name
[**list_partitions**](#list_partitions) | **get** /metalaskes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables/{table}/partitions | List partitions (names)

# **add_partitions**
<a id="add_partitions"></a>
> PartitionListResponse add_partitions(metalakecatalogschematable)

Add partitions

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import partition_api
from pygravitino.model.add_partitions_request import AddPartitionsRequest
from pygravitino.model.partition_list_response import PartitionListResponse
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
    api_instance = partition_api.PartitionApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    try:
        # Add partitions
        api_response = api_instance.add_partitions(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling PartitionApi->add_partitions: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    body = AddPartitionsRequest(
        partitions=[
            PartitionSpec(
                type="IdentityPartition",
                name="name_example",
                field_names=FieldNames([
                    FieldName([
                        "string_example"
                    ])
                ]),
                values=[
                    Literal1(
                        type="literal",
                        data_type=DataType(None),
                        value="value_example",
                    )
                ],
                properties=Properties(
                    key="key_example",
                ),
            )
        ],
    )
    try:
        # Add partitions
        api_response = api_instance.add_partitions(
            path_params=path_params,
            body=body,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling PartitionApi->add_partitions: %s\n" % e)
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
[**AddPartitionsRequest**](../../models/AddPartitionsRequest.md) |  | 


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
200 | [ApiResponseFor200](#add_partitions.ApiResponseFor200) | Returns list of partition objects
409 | [ApiResponseFor409](#add_partitions.ApiResponseFor409) | Conflict - The target partition already exists
5xx | [ApiResponseFor5xx](#add_partitions.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### add_partitions.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**PartitionListResponse**](../../models/PartitionListResponse.md) |  | 


#### add_partitions.ApiResponseFor409
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor409ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor409ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### add_partitions.ApiResponseFor5xx
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

# **get_partition**
<a id="get_partition"></a>
> {str: (bool, date, datetime, dict, float, int, list, str, none_type)} get_partition(metalakecatalogschematablepartition)

Get partition by name

Returns the specified partition

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import partition_api
from pygravitino.model.partition_spec import PartitionSpec
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
    api_instance = partition_api.PartitionApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
        'partition': "partition_example",
    }
    try:
        # Get partition by name
        api_response = api_instance.get_partition(
            path_params=path_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling PartitionApi->get_partition: %s\n" % e)
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
partition | PartitionSchema | | 

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

# PartitionSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
str,  | str,  |  | 

### Return Types, Responses

Code | Class | Description
------------- | ------------- | -------------
n/a | api_client.ApiResponseWithoutDeserialization | When skip_deserialization is True this response is returned
200 | [ApiResponseFor200](#get_partition.ApiResponseFor200) | Returns include the partition object
404 | [ApiResponseFor404](#get_partition.ApiResponseFor404) | Not Found - The specified partition does not exist
5xx | [ApiResponseFor5xx](#get_partition.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### get_partition.ApiResponseFor200
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
**partition** | [**PartitionSpec**]({{complexTypePrefix}}PartitionSpec.md) | [**PartitionSpec**]({{complexTypePrefix}}PartitionSpec.md) |  | [optional] 
**any_string_name** | dict, frozendict.frozendict, str, date, datetime, int, float, bool, decimal.Decimal, None, list, tuple, bytes, io.FileIO, io.BufferedReader | frozendict.frozendict, str, BoolClass, decimal.Decimal, NoneClass, tuple, bytes, FileIO | any string name can be used but the value must be the correct type | [optional]

#### get_partition.ApiResponseFor404
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor404ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor404ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### get_partition.ApiResponseFor5xx
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

# **list_partitions**
<a id="list_partitions"></a>
> bool, date, datetime, dict, float, int, list, str, none_type list_partitions(metalakecatalogschematable)

List partitions (names)

### Example

* Basic Authentication (BasicAuth):
* OAuth Authentication (OAuth2WithJWT):
```python
import pygravitino
from pygravitino.apis.tags import partition_api
from pygravitino.model.partition_list_response import PartitionListResponse
from pygravitino.model.error_model import ErrorModel
from pygravitino.model.partition_name_list_response import PartitionNameListResponse
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
    api_instance = partition_api.PartitionApi(api_client)

    # example passing only required values which don't have defaults set
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    query_params = {
    }
    try:
        # List partitions (names)
        api_response = api_instance.list_partitions(
            path_params=path_params,
            query_params=query_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling PartitionApi->list_partitions: %s\n" % e)

    # example passing only optional values
    path_params = {
        'metalake': "metalake_example",
        'catalog': "catalog_example",
        'schema': "schema_example",
        'table': "table_example",
    }
    query_params = {
        'details': False,
    }
    try:
        # List partitions (names)
        api_response = api_instance.list_partitions(
            path_params=path_params,
            query_params=query_params,
        )
        pprint(api_response)
    except pygravitino.ApiException as e:
        print("Exception when calling PartitionApi->list_partitions: %s\n" % e)
```
### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
query_params | RequestQueryParams | |
path_params | RequestPathParams | |
accept_content_types | typing.Tuple[str] | default is ('application/vnd.gravitino.v1+json', ) | Tells the server the content type(s) that are accepted by the client
stream | bool | default is False | if True then the response.content will be streamed and loaded from a file like object. When downloading a file, set this to True to force the code to deserialize the content to a FileSchema file
timeout | typing.Optional[typing.Union[int, typing.Tuple]] | default is None | the timeout used by the rest client
skip_deserialization | bool | default is False | when True, headers and body will be unset and an instance of api_client.ApiResponseWithoutDeserialization will be returned

### query_params
#### RequestQueryParams

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
details | DetailsSchema | | optional


# DetailsSchema

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
bool,  | BoolClass,  |  | if omitted the server will use the default value of False

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
200 | [ApiResponseFor200](#list_partitions.ApiResponseFor200) | Returns list of partition objects if {details} is true, else returns list of partition names
400 | [ApiResponseFor400](#list_partitions.ApiResponseFor400) | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#x27;s middleware.
5xx | [ApiResponseFor5xx](#list_partitions.ApiResponseFor5xx) | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.

#### list_partitions.ApiResponseFor200
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor200ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor200ResponseBodyApplicationVndGravitinoV1json

## Model Type Info
Input Type | Accessed Type | Description | Notes
------------ | ------------- | ------------- | -------------
dict, frozendict.frozendict, str, date, datetime, uuid.UUID, int, float, decimal.Decimal, bool, None, list, tuple, bytes, io.FileIO, io.BufferedReader,  | frozendict.frozendict, str, decimal.Decimal, BoolClass, NoneClass, tuple, bytes, FileIO |  | 

### Composed Schemas (allOf/anyOf/oneOf/not)
#### oneOf
Class Name | Input Type | Accessed Type | Description | Notes
------------- | ------------- | ------------- | ------------- | -------------
[PartitionNameListResponse]({{complexTypePrefix}}PartitionNameListResponse.md) | [**PartitionNameListResponse**]({{complexTypePrefix}}PartitionNameListResponse.md) | [**PartitionNameListResponse**]({{complexTypePrefix}}PartitionNameListResponse.md) |  | 
[PartitionListResponse]({{complexTypePrefix}}PartitionListResponse.md) | [**PartitionListResponse**]({{complexTypePrefix}}PartitionListResponse.md) | [**PartitionListResponse**]({{complexTypePrefix}}PartitionListResponse.md) |  | 

#### list_partitions.ApiResponseFor400
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
response | urllib3.HTTPResponse | Raw response |
body | typing.Union[SchemaFor400ResponseBodyApplicationVndGravitinoV1json, ] |  |
headers | Unset | headers were not defined |

# SchemaFor400ResponseBodyApplicationVndGravitinoV1json
Type | Description  | Notes
------------- | ------------- | -------------
[**ErrorModel**](../../models/ErrorModel.md) |  | 


#### list_partitions.ApiResponseFor5xx
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

