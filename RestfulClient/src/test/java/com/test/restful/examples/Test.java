/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;

import com.restfulclient.call.RestClientConstants;
import com.restfulclient.call.RestClient;
import com.restfulclient.impl.Authorization;
import com.restfulclient.impl.BodyType;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.ParameterType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odge
 */
public class Test {

    public static void main(String args[]) {

        try {
            
            String apiKey = null;
            
            String body = "{\n"
                    + "  \"name\": \"Jhon Doe\",\n"
                    + "  \"location\": \"Canada\",\n"
                    + "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";
            
            var headerMap = new LinkedHashMap<String, Object>();
            headerMap.put(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE);
            headerMap.put(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT);
            headerMap.put(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT);
            headerMap.put(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE);
            
            /**
             * ****************POST Example**************************
             */
            
            try {
                var response = RestClient.build("http://localhost:8088/api/test",
                        "savePerson",
                        Method.POST,
                        BodyType.RAW,
                        headerMap,
                        body
                        ).addAutentication(apiKey).executeCall();
                var mp = response.getMap();
                System.out.println(mp.toString());
                var content = response.getResponseContent();
                System.out.println(content);
            } catch (Exception ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            /**
             * ****************GET and PATH_VARIABLES example******************
             */
            var queryParams = new LinkedHashMap<String, Object>();
            queryParams.put("id", 10001);
            
            var response= RestClient.build("http://localhost:8088/api/test", "getPerson", Method.GET, headerMap, ParameterType.PATH_VARIABLES, 
                    queryParams).addAutentication(apiKey).executeCall();
         
            
            var content = response.getResponseContent();
            System.out.println(content);
            /**
             * ****************GET and QUERY_PARAMS example******************
             */
            queryParams.put("id", 10001);
            RestClient.build("http://localhost:8088/api/test", "getPerson", Method.GET, headerMap, ParameterType.QUERY_PARAMS, 
                     queryParams).addAutentication(apiKey).executeCall();
            
            
            content = response.getResponseContent();
            System.out.println(content);
            
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
