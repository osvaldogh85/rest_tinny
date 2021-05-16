/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;


import com.restfulclient.impl.Call;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.ICall;
import java.util.Map;

public class RestClient extends Call {

    private final String serverURL;
    private final String requestMethodURL;
    private final String JSONBodyContent;
    private final Method method;
    private final RequestType TYPE_SELECTED;
    private final String mediaType;
    private Map<String,Object> queryParameters=null;


    private RestClient(String serverURL, String requestMethodPath,Method method,RequestType requestType, String mediaType,String JSONBodyContent, String apiToken) {
        super(apiToken,null,null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;
        this.method=method;
        this.TYPE_SELECTED=requestType;
        this.mediaType=mediaType;
       
    }
    
    private RestClient(String serverURL, String requestMethodPath,Method method,String mediaType, String apiToken) {
        super(apiToken,null,null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;
        this.method=method;
        this.TYPE_SELECTED=Call.TYPE_JSON_QUERY;
        this.mediaType=mediaType;
       
    }
    
     private RestClient(String serverURL, String requestMethodPath,Method method,String mediaType,String user, String password) {
        super(null,user,password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;
        this.method=method;
        this.TYPE_SELECTED=Call.TYPE_JSON_QUERY;
        this.mediaType=mediaType;
       
    }
      
    private RestClient(String serverURL, String requestMethodPath, Method method,RequestType requestType,String mediaType,String JSONBodyContent, String user, String password) {
        super(null,user,password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;   
        this.method=method;
        this.TYPE_SELECTED=requestType;
        this.mediaType=mediaType;
    }
    
     private RestClient(String serverURL, String requestMethodPath,Method method,RequestType requestType,String mediaType, Map<String,Object> queryParameters, String apiToken) {
        super(apiToken,null,null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;
        this.method=method;
        this.TYPE_SELECTED=requestType;
        this.queryParameters=queryParameters;
        this.mediaType=mediaType;
       
    }
      
    private RestClient(String serverURL, String requestMethodPath, Method method,RequestType requestType,String mediaType,Map<String,Object> queryParameters, String user, String password) {
        super(null,user,password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = null;   
        this.method=method;
        this.TYPE_SELECTED=requestType;
        this.queryParameters=queryParameters;
        this.mediaType=mediaType;
    }
    
    public static ICall build(String serverURL, String requestMethodPath,Method method,String mediaType, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method,mediaType, apiToken);
    }
    
    public static ICall build(String serverURL, String requestMethodPath,Method method,String mediaType, String user, String password) {
        return new RestClient(serverURL, requestMethodPath, method,mediaType,  user, password);
    }
     
    public static ICall build(String serverURL, String requestMethodPath,Method method,RequestType requestType,String mediaType, Map<String,Object> queryParameters, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method,requestType,mediaType, queryParameters, apiToken);
    }
    
    public static ICall build(String serverURL, String requestMethodPath, Method method,RequestType requestType,String mediaType,  Map<String,Object> queryParameters, String user, String password) {
        return new RestClient(serverURL, requestMethodPath,method,requestType,mediaType, queryParameters,  user, password);
    }
    
    public static ICall build(String serverURL, String requestMethodPath,Method method,RequestType requestType, String mediaType,String JSONBodyContent, String apiToken) {
        return new RestClient(serverURL, requestMethodPath, method,requestType,mediaType, JSONBodyContent, apiToken);
    }

    public static ICall build(String serverURL, String requestMethodPath, Method method,RequestType requestType,String mediaType, String JSONBodyContent, String user, String password) {
        return new RestClient(serverURL, requestMethodPath,method,requestType,mediaType, JSONBodyContent, user, password);
    }     

    @Override
    public void prepareCall() {
        super.prepareCall();
        super.addHeader(Constants.API_CONTENT,mediaType);
         
        if (TYPE_SELECTED == Call.TYPE_JSON_QUERY || TYPE_SELECTED == Call.TYPE_HTTP_QUERY) {   
            if(queryParameters !=null){
               queryParameters.keySet().forEach(key -> {
                  super.addQueryParameter(key, queryParameters.get(key));           
               });
             }
        } else {
            if (TYPE_SELECTED == Call.TYPE_JSON_BODY) {
                super.addRequestBody(BodyContent.build(JSONBodyContent));
            }
        }
        
        super.addURLInfo(serverURL, requestMethodURL,method,TYPE_SELECTED);
       
    }   
}
