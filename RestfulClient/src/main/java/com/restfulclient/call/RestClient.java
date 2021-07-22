/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractCall;
import com.restfulclient.impl.AuthorizationImpl;
import com.restfulclient.impl.CallType;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.IAuthorization;
import com.restfulclient.interfaces.IHTTPCall;
import java.util.Map;

public class RestClient extends AbstractCall {

  private final String serverURL;
  private final String requestMethodURL;
  private final String bodyContent;
  private final Method method;
  private final CallType TYPE_SELECTED;
  private Map<String, Object> queryParameters = null;
  private Map<String, Object> optionalHeaderParameters = null;
  private String apiToken=null, user=null, pass=null;

  private RestClient(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = bodyContent;
    this.method = method;
    this.TYPE_SELECTED = requestType;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.apiToken = apiToken;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = null;
    this.method = method;
    this.TYPE_SELECTED = CallType.TYPE_BASIC_JSON_QUERY;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.apiToken = apiToken;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = null;
    this.method = method;
    this.TYPE_SELECTED = CallType.TYPE_BASIC_JSON_QUERY;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.user = user;
    this.pass = password;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = bodyContent;
    this.method = method;
    this.TYPE_SELECTED = requestType;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.user = user;
    this.pass = password;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = null;
    this.method = method;
    this.TYPE_SELECTED = requestType;
    this.queryParameters = queryParameters;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.apiToken = apiToken;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = null;
    this.method = method;
    this.TYPE_SELECTED = requestType;
    this.queryParameters = queryParameters;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.user = user;
    this.pass = password;
  }

  private RestClient(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
    this.serverURL = serverURL;
    this.requestMethodURL = requestMethodPath;
    this.bodyContent = bodyContent;
    this.method = method;
    this.TYPE_SELECTED = requestType;
    this.queryParameters = queryParameters;
    this.optionalHeaderParameters = optionalHeaderParameters;
    this.user = user;
    this.pass = password;
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
    return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, apiToken);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
    return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, user, password);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
    return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, apiToken);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
    return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, user, password);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
    return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, apiToken);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
    return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, user, password);
  }

  public static IHTTPCall build(String serverURL, String requestMethodPath, Method method, CallType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
    return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, bodyContent, user, password);
  }

  @Override
  public void addAutentication() {
    IAuthorization authorization =null;
    if (apiToken != null && !apiToken.equals("")) {
      authorization= AuthorizationImpl.build(apiToken);
    }

    if (user != null && !user.equals("") && pass != null && !pass.equals("")) {
      authorization= AuthorizationImpl.build(user, pass);
    }
    if (authorization != null) {
      this.addHeader(authorization.getAuthorization(), authorization.getAuthorizationToken());
    }
  }

  @Override
  public void prepareCall() {
    optionalHeaderParameters.keySet().forEach(param -> {
      super.addHeader(param, optionalHeaderParameters.get(param).toString());
    });

    if (TYPE_SELECTED == CallType.TYPE_BASIC_JSON_QUERY_AND_BODY || TYPE_SELECTED == CallType.TYPE_BASIC_HTTP_QUERY_AND_BODY) {
      if (queryParameters != null) {
        queryParameters.keySet().forEach(key -> {
          super.addQueryParameter(key, queryParameters.get(key));
        });
      }
      super.addRequestBody(BodyContent.build(bodyContent));
    } else {
      if (TYPE_SELECTED == CallType.TYPE_BASIC_HTTP_QUERY || TYPE_SELECTED == CallType.TYPE_BASIC_JSON_QUERY) {
        if (queryParameters != null) {
          queryParameters.keySet().forEach(key -> {
            super.addQueryParameter(key, queryParameters.get(key));
          });
        }
      } else {
        if (TYPE_SELECTED == CallType.TYPE_JSON_BODY) {
          super.addRequestBody(BodyContent.build(bodyContent));
        }
      }
    }

    super.addURLInfo(serverURL, requestMethodURL, method, TYPE_SELECTED);
  }
}
