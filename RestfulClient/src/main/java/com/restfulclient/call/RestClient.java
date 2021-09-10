/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractClient;
import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.Authorization;
import com.restfulclient.impl.BodyType;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.ParameterType;
import com.restfulclient.impl.RequestBody;
import com.restfulclient.impl.RequestParameters;
import com.restfulclient.interfaces.IAuthorization;
import java.io.File;
import java.util.Map;
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestParameters;
import java.util.LinkedHashMap;

public class RestClient extends AbstractClient {

    private String bodyContent = null;
    private BodyType bodyType = BodyType.NONE;;
    private ParameterType parameterType=null;
    private LinkedHashMap<String, Object> queryParameters = null;
    private LinkedHashMap<String, Object> multiPartFormData = new LinkedHashMap<String, Object>();
    private Map<String, Object> headerParameters = null;
    
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType, Map<String, Object> headerParameters, String bodyContent) throws Exception {
        super(serverURL,requestMethodPath,method);
        this.bodyContent = bodyContent;  
        this.headerParameters = headerParameters;
        this.bodyType=bodyType;
    }
    
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType, Map<String, Object> headerParameters, File file) throws  ApiException, Exception {
        super(serverURL,requestMethodPath,method);       
        super.addFormMultipartParams(file.getName(), file);        
        this.bodyType=bodyType;     
        this.headerParameters = headerParameters;
        multiPartFormData.put(file.getName(), file);
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> headerParameters) throws Exception {
        super(serverURL,requestMethodPath,method);    
        this.headerParameters = headerParameters;
    }

     private RestClient(String serverURL, String requestMethodPath, Method method, Map<String, Object> headerParameters, ParameterType parameterType, LinkedHashMap<String, Object> queryParameters) throws Exception {
        super(serverURL,requestMethodPath,method);
        this.parameterType=parameterType;
        this.queryParameters = queryParameters;
        this.headerParameters = headerParameters;
    }

    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType, Map<String, Object> headerParameters,  ParameterType parameterType,LinkedHashMap<String, Object> queryParameters, String bodyContent) throws Exception {
        super(serverURL,requestMethodPath,method);
        this.bodyContent = bodyContent;     
        this.parameterType=parameterType;
        this.queryParameters = queryParameters;
        this.headerParameters = headerParameters;
        this.bodyType=bodyType;
    }
  
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType, Map<String, Object> headerParameters,  ParameterType parameterType, LinkedHashMap<String, Object> queryParameters, File file) throws ApiException, Exception {
        super(serverURL,requestMethodPath,method);
        super.addFormMultipartParams(file.getName(), file);       
        this.queryParameters = queryParameters;
        this.parameterType=parameterType;
        this.headerParameters = headerParameters;
        this.bodyType=bodyType;
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType, Map<String, Object> headerParameters, ParameterType parameterType,LinkedHashMap<String, Object> queryParameters, String bodyContent) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType, headerParameters, parameterType,queryParameters, bodyContent );
    }
 
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, Map<String, Object> headerParameters) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, headerParameters);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method,  Map<String, Object> headerParameters, ParameterType parameterType, LinkedHashMap<String, Object> queryParameters) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, headerParameters, parameterType,queryParameters);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType, Map<String, Object> headerParameters, String bodyContent) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType, headerParameters, bodyContent);
    }
    
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType, Map<String, Object> headerParameters, File file) throws ApiException, Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType, headerParameters, file);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType, Map<String, Object> headerParameters, ParameterType parameterType,LinkedHashMap<String, Object> queryParameters, File file) throws ApiException, Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType, headerParameters, parameterType,queryParameters, file);
    }
   
    @Override
    public IAbstractClient addAutentication(String apiKey) {     
        if (apiKey != null) {
            IAuthorization build = Authorization.build(apiKey);
            this.addHeader(build.getAuthorization(), build.getAuthorizationToken());
        }
       return this;
    }
    
    @Override
     public IAbstractClient addAutentication(String user,String password) {     
        if (user != null && password != null) {
            IAuthorization build = Authorization.build(user,password);
            this.addHeader(build.getAuthorization(), build.getAuthorizationToken());
        }
       return this;
    }

    @Override
    public void prepareCall() throws ApiException {
        try {
            addHeaderParameters();
            switch(bodyType){
                case RAW:
                    addRequestParameter();
                    super.addRequestBody(bodyContent != null ? RequestBody.build(bodyType,bodyContent.getBytes()) : null);       
                    break;
                case URL_FORM_ENCODED:
                    bodyContent=RestClientConstants.urlencoded(queryParameters, null);
                    super.addRequestBody(bodyContent != null ? RequestBody.build(bodyType,bodyContent.getBytes()) : null);        
                    break;
                case MULTIPART_FORM:
                    addRequestParameter();
                    addMultiFormData();
                    break;
                case FORM_DATA:
                    addRequestParameter();
                    addMultiFormData();
                    break;
                case BINARY:
                    addMultiFormData();                
                   break;
                case NONE:
                    addRequestParameter();
                    break;
                default:
                          
            }  
        } catch (Exception ex) { 
            throw new ApiException(ex);
        }
    }
    
    private void addRequestParameter() throws ApiException{
      if (queryParameters != null && !queryParameters.isEmpty()) {
         IRequestParameters parameter = RequestParameters.build();
         try {
            parameter.addParameter(parameterType, queryParameters);
            super.addRequestParameter(parameter);
         } catch (Exception ex) {
           throw new ApiException(ex);
         }
     }
   }
    
    private void addMultiFormData(){
     if(multiPartFormData != null && !multiPartFormData.isEmpty()){
         multiPartFormData.keySet().forEach(key -> {
          super.addFormMultipartParams(key,multiPartFormData.get(key));
         });
       multiPartFormData.clear();
     }
   }
    
    private void addHeaderParameters(){
       if(headerParameters!= null) {
          headerParameters.keySet().forEach(param -> {
               super.addHeader(param, headerParameters.get(param).toString());
          });
        headerParameters.clear();
      }
    }
}
