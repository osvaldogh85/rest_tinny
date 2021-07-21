/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.HTTPBase;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.IHTTPCall;
import java.util.Map;

public class RestClient extends HTTPBase {

    private final String serverURL;
    private final String requestMethodURL;
    private final String bodyContent;
    private final Method method;
    private final RequestType TYPE_SELECTED;
    private Map<String, Object> queryParameters = null;
    private Map<String, Object> optionalHeaderParameters = null;

    private RestClient(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
        super(apiToken, null, null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;
        this.TYPE_SELECTED = requestType;
        this.optionalHeaderParameters = optionalHeaderParameters;

    }

    private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
        super(apiToken, null, null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;
        this.TYPE_SELECTED = RequestType.TYPE_JSON_QUERY;
        this.optionalHeaderParameters = optionalHeaderParameters;

    }

    private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;
        this.TYPE_SELECTED = RequestType.TYPE_JSON_QUERY;
        this.optionalHeaderParameters = optionalHeaderParameters;

    }

    private RestClient(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;
        this.TYPE_SELECTED = requestType;
        this.optionalHeaderParameters = optionalHeaderParameters;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
        super(apiToken, null, null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;
        this.TYPE_SELECTED = requestType;
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;

    }

    private RestClient(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;
        this.TYPE_SELECTED = requestType;
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;
        this.TYPE_SELECTED = requestType;
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, apiToken);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, user, password);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, apiToken);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, user, password);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, apiToken);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, user, password);
    }

    public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, RequestType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, bodyContent, user, password);
    }

    @Override
    public void prepareCall() {
        super.prepareCall();
        optionalHeaderParameters.keySet().forEach(param -> {
            super.addHeader(param, optionalHeaderParameters.get(param).toString());
        });

        if (TYPE_SELECTED == RequestType.TYPE_JSON_QUERY_AND_BODY || TYPE_SELECTED == RequestType.TYPE_HTTP_QUERY_AND_BODY) {
            if (queryParameters != null) {
                queryParameters.keySet().forEach(key -> {
                    super.addQueryParameter(key, queryParameters.get(key));
                });
            }
            super.addRequestBody(BodyContent.build(bodyContent));
        } else {
            if (TYPE_SELECTED == RequestType.TYPE_HTTP_QUERY || TYPE_SELECTED == RequestType.TYPE_JSON_QUERY) {
                if (queryParameters != null) {
                    queryParameters.keySet().forEach(key -> {
                        super.addQueryParameter(key, queryParameters.get(key));
                    });
                }
            } else {
                if (TYPE_SELECTED == RequestType.TYPE_JSON_BODY) {
                    super.addRequestBody(BodyContent.build(bodyContent));
                }
            }
        }

        super.addURLInfo(serverURL, requestMethodURL, method, TYPE_SELECTED);
    }
}
