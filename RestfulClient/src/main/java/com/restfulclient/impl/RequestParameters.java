/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.impl;

import com.restfulclient.call.RestClientConstants;
import com.restfulclient.interfaces.IRequestParameters;
import java.util.LinkedHashMap;

/**
 *
 * @author odge
 */
public final class RequestParameters implements IRequestParameters {

    private LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
    private StringBuilder parametersEncoded = null;
    private ParameterType type = null;
    
    private RequestParameters(ParameterType type) {      
         this.type = type;
    }
        
    public static IRequestParameters build(ParameterType type) {
        return new RequestParameters(type);
    }
  
    @Override
    public void buildParameters() throws ApiException {       
        switch (type) {
            case PATH_VARIABLES:
                if (parameters.isEmpty()) {
                    throw new ApiException("Error while creating path there is no parameters to json request path");
                }
                // request should be like this https://server_url/methodName/000-00-000003-1
                parametersEncoded = new StringBuilder();
                //URL  https://server/up/v5/
                //key                 value
                //workItem          workItem
                //workItem_value    00-00-000003-1
                parameters.keySet().forEach(key -> {
                    parametersEncoded.append("/").append(parameters.get(key).toString());
                    //1 loop https://server_url/methodName
                    //2 loop https://server_url/methodName/000-00-000003-1
                });              
                break;
            case QUERY_PARAMS:
                if (parameters.isEmpty()) {
                       throw new ApiException("Error while creating path there is no parameters to json request path");
                }
                parametersEncoded = new StringBuilder();
                // https://server/methodName
                //parameter map format
                //key                 value
                //param1             value1
                //param2             value2
                //***************************
                //paramN-1             valueN-1
                 parametersEncoded.append("?");
                var paramCount = parameters.keySet().size() - 1;
                var count = 0;
                // https://server_url/methodName?
                for (String key : parameters.keySet()) {
                    if (count < paramCount) {
                        //loop https://server_url/methodName?param1=value1&param2=value3&
                        parametersEncoded.append(key.trim()).append("=").append(parameters.get(key).toString().trim()).append("&");
                    } else {
                        //else https://server_url/methodName?param1=value1&param2=value3&paramN-1=valueN-1
                        parametersEncoded.append(key.trim()).append("=").append(parameters.get(key).toString().trim());
                    }
                    count++;
                }              
                break;
            case FOR_URL_ENCODED:
                 if (parameters.isEmpty()) {
                       throw new ApiException("Error while creating path there is no parameters to json request path");
                }
                parametersEncoded = new StringBuilder();
                parametersEncoded.append(RestClientConstants.urlencoded(parameters, null));             
                break;
        }
     
    }

    @Override
    public void addParameter(String key , Object value) {       
        this.parameters.put(key, value);       
    }

    @Override
    public void clean() {
       this.parameters.clear();
       this.parameters=null;
       this.parametersEncoded=null;
       this.type=null;
    }
   
    @Override
    public ParameterType getParamterType() {
        return type;
    }

    @Override
    public StringBuilder getEncodedParameters() {
          return parametersEncoded;
    }

}
