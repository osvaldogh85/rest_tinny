/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractCall;
import com.restfulclient.impl.Call;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.ICall;
import java.util.Map;

public class DeleteCall extends Call {
    

    private final RequestType TYPE_SELECTED;
    private final String serverURL;
    private final String requestMethodURL;
    private final String JSONBodyContent;
    private final Map<String, String> queryParameters;

    private DeleteCall(String serverURL, String requestMethodPath, String apiToken, RequestType type, Map<String, String> queryParameters) {
        super(apiToken, null, null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;
        this.queryParameters = queryParameters;
        this.TYPE_SELECTED = type;
    }

    private DeleteCall(String serverURL, String requestMethodPath, String apiToken, RequestType type, String JSONBodyContent) {
        super(apiToken, null, null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;
        this.queryParameters = null;
        this.TYPE_SELECTED = type;
    }

    private DeleteCall(String serverURL, String requestMethodPath, String JSONBodyContent, String user, String password, RequestType type) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;
        this.queryParameters = null;
        this.TYPE_SELECTED = type;
    }

    private DeleteCall(String serverURL, String requestMethodPath, String user, String password, RequestType type, Map<String, String> queryParameters) {
        super(null, user, password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;
        this.queryParameters = queryParameters;
        this.TYPE_SELECTED = type;
    }

    public static ICall build(String serverURL, String requestMethodPath, String apiToken, RequestType type, Map<String, String> queryParameters) {
        return new DeleteCall(serverURL, requestMethodPath, apiToken, type, queryParameters);
    }

    public static ICall build(String serverURL, String requestMethodPath, String apiToken, RequestType type, String JSONBodyContent) {
        return new DeleteCall(serverURL, requestMethodPath, apiToken, type, JSONBodyContent);
    }

    public static ICall build(String serverURL, String requestMethodPath, String JSONBodyContent, String user, String password, RequestType type) {
        return new DeleteCall(serverURL, requestMethodPath, JSONBodyContent, user, password, type);
    }

    public static ICall build(String serverURL, String requestMethodPath, Map<String, String> queryParameters, String user, String password, RequestType type) {
        return new DeleteCall(serverURL, requestMethodPath, user, password, type, queryParameters);
    }

    @Override
    public void prepareCall() {
        super.prepareCall();
         if (TYPE_SELECTED == AbstractCall.TYPE_JSON_QUERY || TYPE_SELECTED == AbstractCall.TYPE_HTTP_QUERY) {           
            queryParameters.keySet().forEach(key -> {
                super.addQueryParameter(key, queryParameters.get(key));
            });
        } else {
            if (TYPE_SELECTED == AbstractCall.TYPE_JSON_BODY) {
                super.addRequestBody(BodyContent.build(JSONBodyContent));
            }
        }
        super.addURLInfo(serverURL, requestMethodURL, Method.DELETE, TYPE_SELECTED);
       
    }
}
