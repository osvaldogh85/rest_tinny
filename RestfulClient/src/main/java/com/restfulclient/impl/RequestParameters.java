/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestParameters;
import java.util.LinkedHashMap;

/**
 *
 * @author odge
 */
public class RequestParameters implements IRequestParameters {

    private LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
    private StringBuilder parametersEncoded = null;
    
    private RequestParameters() {        
    }
    
    public static IRequestParameters build() {
        return new RequestParameters();
    }

  
    private void processParameters(ParameterType type) throws Exception {
        switch (type) {
            case PATH_VARIABLES:
                if (parameters.isEmpty()) {
                    throw new Exception("Error while creating path there is no parameters to json request path");
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
                parameters.clear();
                break;
            case QUERY_PARAMS:
                if (parameters.isEmpty()) {
                    return;
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
                parameters.clear();
                break;
        }
    }

    @Override
    public void addParameter(ParameterType type,LinkedHashMap<String, Object> params) throws Exception {       
        params.keySet().forEach(key -> {
           this.parameters.put(key, params.get(key));
        });
        params.clear();
        
        processParameters(type);
    }

    @Override
    public String getEncodedParameters() {
        if(parametersEncoded!=null)
          return parametersEncoded.toString();
        
        return null;
    }

    @Override
    public void clean() {
       this.parameters=null;
       this.parametersEncoded=null;
    }

}
