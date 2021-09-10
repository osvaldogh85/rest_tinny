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
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestParameters;
import java.util.LinkedHashMap;

public class RestClient extends AbstractClient {

    private String bodyContent = null;
    private BodyType bodyType = BodyType.NONE;;
    private LinkedHashMap<String, Object> multiPartFormData = new LinkedHashMap<String, Object>();
    
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType, String bodyContent) throws Exception {
        super(serverURL,requestMethodPath,method);
        this.bodyContent = bodyContent;  
        this.bodyType=bodyType;
    }
    
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType,File file) throws  ApiException, Exception {
        super(serverURL,requestMethodPath,method);       
        super.addFormMultipartParams(file.getName(), file);        
        this.bodyType=bodyType;     
        multiPartFormData.put(file.getName(), file);
    }

    private RestClient(String serverURL, String requestMethodPath, Method method) throws Exception {
        super(serverURL,requestMethodPath,method);    
    }
    
    private RestClient(String serverURL, String requestMethodPath, Method method, BodyType bodyType) throws Exception {
        super(serverURL,requestMethodPath,method);
        this.bodyType=bodyType;
    }  
    
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method,  BodyType bodyType) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, bodyType);
    }

    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType ,String bodyContent) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType,bodyContent );
    }
 
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method);
    }
    
    public static IAbstractClient build(String serverURL, String requestMethodPath, Method method, BodyType requestType,File file) throws ApiException, Exception {
        return new RestClient(serverURL, requestMethodPath, method, requestType, file);
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
            switch(bodyType){
                case RAW:     
                    super.addRequestBody(bodyContent != null ? RequestBody.build(bodyType,bodyContent.getBytes()) : null);  
                    break;
                case URL_FORM_ENCODED:
                    bodyContent= super.getRequestPath().getEncodedParameters();
                    super.addRequestBody(bodyContent != null ? RequestBody.build(bodyType,bodyContent.getBytes()) : null);  
                    break;
                case MULTIPART_FORM:        
                    addMultiFormData();
                    break;
                case FORM_DATA:
                    addMultiFormData();
                    break;
                case BINARY:
                    addMultiFormData();  
                    break;
                case NONE:                   
                    break;
                default:                       
            }  
        } catch (Exception ex) { 
            throw new ApiException(ex);
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
   
}
