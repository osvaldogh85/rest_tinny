/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractClient;
import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.Authorization;
import com.restfulclient.impl.MediaType;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.RequestBody;
import com.restfulclient.interfaces.IAuthorization;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import com.restfulclient.interfaces.IAbstractClient;


public class RestClient extends AbstractClient {

    private final String serverURL;
    private final String requestMethodURL;
    private byte[] bodyContent = null;
    private final Method method;
    private Map<String, Object> queryParameters = null;
    private Map<String, Object> optionalHeaderParameters = null;
    private String apiToken = null, user = null, pass = null;
       
    
    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent.getBytes();
        this.method = method;      
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.apiToken = apiToken;
    }
    
     private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, byte[] bodyContent, String apiToken) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;      
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.apiToken = apiToken;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, File multiPart, String apiToken) throws  ApiException {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        try (FileInputStream fis = new FileInputStream(multiPart)) {
            this.bodyContent = fis.readAllBytes();
            super.addFormMultipartParams(multiPart.getName(), multiPart);
        } catch (FileNotFoundException ex) {
            throw new ApiException(ex);
        } catch (IOException ex) {
            throw new ApiException(ex);
        }
        this.method = method;       
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.apiToken = apiToken;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
        super(MediaType.JSON_QUERY);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;        
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.apiToken = apiToken;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
        super(MediaType.JSON_QUERY);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;       
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent.getBytes();
        this.method = method;       
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }
    
     private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, byte[] bodyContent, String user, String password) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;       
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, File multiPart, String user, String password) throws ApiException {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
         try (FileInputStream fis = new FileInputStream(multiPart)) {
            this.bodyContent = fis.readAllBytes();
            super.addFormMultipartParams(multiPart.getName(), multiPart);
        } catch (FileNotFoundException ex) {
            throw new ApiException(ex);
        } catch (IOException ex) {
            throw new ApiException(ex);
        }
        this.method = method;        
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;       
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.apiToken = apiToken;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = null;
        this.method = method;      
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent.getBytes();
        this.method = method;       
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }
    
     private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, byte[] bodyContent, String user, String password) {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.bodyContent = bodyContent;
        this.method = method;       
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }
   
    private RestClient(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, File multiPart, String user, String password) throws ApiException {
        super(requestType);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        try (FileInputStream fis = new FileInputStream(multiPart)) {
            this.bodyContent = fis.readAllBytes();
            super.addFormMultipartParams(multiPart.getName(), multiPart);
        } catch (FileNotFoundException ex) {
             throw new ApiException(ex);
        } catch (IOException ex) {
             throw new ApiException(ex);
        }
        this.method = method;       
        this.queryParameters = queryParameters;
        this.optionalHeaderParameters = optionalHeaderParameters;
        this.user = user;
        this.pass = password;
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, apiToken);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, Map<String, Object> optionalHeaderParameters, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, optionalHeaderParameters, user, password);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, apiToken);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, user, password);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, apiToken);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, String bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, user, password);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, byte[] bodyContent, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, apiToken);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, byte[] bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, bodyContent, user, password);
    }
    
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, String bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, bodyContent, user, password);
    }
    
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, byte[] bodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, bodyContent, user, password);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, File multiPart, String apiToken) throws ApiException {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, multiPart, apiToken);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, File multiPart, String user, String password) throws ApiException {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, multiPart, user, password);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, MediaType requestType, Map<String, Object> optionalHeaderParameters, Map<String, Object> queryParameters, File multiPart, String user, String password) throws ApiException {
        return new RestClient(serverURL, requestMethodPath, method, requestType, optionalHeaderParameters, queryParameters, multiPart, user, password);
    }
   

    @Override
    public void addAutentication() {
        IAuthorization authorization = null;
        if (apiToken != null && !apiToken.equals("")) {
            authorization = Authorization.build(apiToken);
        }

        if (user != null && !user.equals("") && pass != null && !pass.equals("")) {
            authorization = Authorization.build(user, pass);
        }
        if (authorization != null) {
            this.addHeader(authorization.getAuthorization(), authorization.getAuthorizationToken());
        }
    }

    @Override
    public void prepareCall() {
        if(optionalHeaderParameters!= null) {
           optionalHeaderParameters.keySet().forEach(param -> {
               super.addHeader(param, optionalHeaderParameters.get(param).toString());
            });
           optionalHeaderParameters.clear();
         }
        if (queryParameters != null) {
            queryParameters.keySet().forEach(key -> {
               super.addQueryParameter(key, queryParameters.get(key));
            });
            queryParameters.clear();
        }
        super.addRequestBody(bodyContent != null ? RequestBody.build(bodyContent) : null);       
        super.addURLInfo(serverURL, requestMethodURL, method);
    } 
}
