
<a name="definitions"></a>
## 定义

<a name="3625c36a8ea9cf7285b4f7df3f9f33df"></a>
### JsonResult«object»

|名称|说明|类型|
|---|---|---|
|**data**  <br>*可选*|**样例** : `"object"`|object|
|**dateFmt**  <br>*可选*|**样例** : `"string"`|string|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**onlyData**  <br>*可选*|**样例** : `true`|boolean|
|**status**  <br>*可选*|**样例** : `"string"`|string|
|**total**  <br>*可选*|**样例** : `0`|integer (int32)|
|**type**  <br>*可选*|**样例** : `"string"`|string|


<a name="modelandview"></a>
### ModelAndView

|名称|说明|类型|
|---|---|---|
|**empty**  <br>*可选*|**样例** : `true`|boolean|
|**model**  <br>*可选*|**样例** : `"object"`|object|
|**modelMap**  <br>*可选*|**样例** : `{<br>  "string" : "object"<br>}`|< string, object > map|
|**reference**  <br>*可选*|**样例** : `true`|boolean|
|**status**  <br>*可选*|**样例** : `"string"`|enum (ACCEPTED, ALREADY_REPORTED, BAD_GATEWAY, BAD_REQUEST, BANDWIDTH_LIMIT_EXCEEDED, CHECKPOINT, CONFLICT, CONTINUE, CREATED, DESTINATION_LOCKED, EXPECTATION_FAILED, FAILED_DEPENDENCY, FORBIDDEN, FOUND, GATEWAY_TIMEOUT, GONE, HTTP_VERSION_NOT_SUPPORTED, IM_USED, INSUFFICIENT_SPACE_ON_RESOURCE, INSUFFICIENT_STORAGE, INTERNAL_SERVER_ERROR, I_AM_A_TEAPOT, LENGTH_REQUIRED, LOCKED, LOOP_DETECTED, METHOD_FAILURE, METHOD_NOT_ALLOWED, MOVED_PERMANENTLY, MOVED_TEMPORARILY, MULTIPLE_CHOICES, MULTI_STATUS, NETWORK_AUTHENTICATION_REQUIRED, NON_AUTHORITATIVE_INFORMATION, NOT_ACCEPTABLE, NOT_EXTENDED, NOT_FOUND, NOT_IMPLEMENTED, NOT_MODIFIED, NO_CONTENT, OK, PARTIAL_CONTENT, PAYLOAD_TOO_LARGE, PAYMENT_REQUIRED, PERMANENT_REDIRECT, PRECONDITION_FAILED, PRECONDITION_REQUIRED, PROCESSING, PROXY_AUTHENTICATION_REQUIRED, REQUESTED_RANGE_NOT_SATISFIABLE, REQUEST_ENTITY_TOO_LARGE, REQUEST_HEADER_FIELDS_TOO_LARGE, REQUEST_TIMEOUT, REQUEST_URI_TOO_LONG, RESET_CONTENT, SEE_OTHER, SERVICE_UNAVAILABLE, SWITCHING_PROTOCOLS, TEMPORARY_REDIRECT, TOO_MANY_REQUESTS, UNAUTHORIZED, UNAVAILABLE_FOR_LEGAL_REASONS, UNPROCESSABLE_ENTITY, UNSUPPORTED_MEDIA_TYPE, UPGRADE_REQUIRED, URI_TOO_LONG, USE_PROXY, VARIANT_ALSO_NEGOTIATES)|
|**view**  <br>*可选*|**样例** : `"[view](#view)"`|[View](#view)|
|**viewName**  <br>*可选*|**样例** : `"string"`|string|


<a name="view"></a>
### View

|名称|说明|类型|
|---|---|---|
|**contentType**  <br>*可选*|**样例** : `"string"`|string|



