/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractClient;
import com.restfulclient.impl.Authorization;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.ParameterType;
import com.restfulclient.interfaces.IAuthorization;
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestBody;

public class RestClient extends AbstractClient {
    
    private RestClient(String serverURL, String requestMethodPath, Method method) throws Exception {
        super(serverURL,requestMethodPath,method);
    }
    
    public static IAbstractClient build(String serverURL, String requestMethodPath,Method method) throws Exception {
        return new RestClient(serverURL, requestMethodPath, method);
    }
   
    @Override
    public RestClient addAutentication(String apiKey) {     
        if (apiKey != null) {
            IAuthorization build = Authorization.build(apiKey);
            this.addHeader(build.getAuthorization(), build.getAuthorizationToken());
        }
       return this;
    }
    
    @Override
     public RestClient addAutentication(String user,String password) {     
        if (user != null && password != null) {
            IAuthorization build = Authorization.build(user,password);
            this.addHeader(build.getAuthorization(), build.getAuthorizationToken());
        }
       return this;
    }    
    
    @Override
    public RestClient addHeader(String name, Object value) {      
        super.addHeader(name, value);
        return this;
    }
    
    @Override
    public RestClient addFormMultipartParams(String name, Object value) {
        super.addFormMultipartParams(name, value);  
        return this;
    }

    @Override
    public RestClient addRequestBody(IRequestBody body) {       
        super.addRequestBody(body);  
        return this;
    }

   @Override
    public RestClient addNewRequestQueryParameter(String name, Object value)throws Exception {
      super.addNewRequestQueryParameter(name, value);
      return this;
    } 
    
    @Override
    public RestClient addNewRequestPathParameter(String name, Object value)throws Exception {
      super.addNewRequestPathParameter(name, value);
      return this;
    } 
    
    @Override
    public RestClient addNewPathParameter(String name, Object value)throws Exception {
      super.addNewPathParameter(name, value);
      return this;
    }
     
} 
   
   

