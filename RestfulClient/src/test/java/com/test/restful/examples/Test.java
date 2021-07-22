/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;

import com.restfulclient.call.RestClientConstants;
import com.restfulclient.call.RestClient;
import com.restfulclient.impl.CallType;
import com.restfulclient.impl.Method;
import java.util.HashMap;

/**
 *
 * @author odge
 */
public class Test {

    public static void main(String args[]) {

        String apiKey = null;

        String body = "{\n"
                + "  \"name\": \"Jhon Doe\",\n"
                + "  \"location\": \"Canada\",\n"
                + "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";

        var headerMap = new HashMap<String, Object>();
        headerMap.put(RestClientConstants.API_CONTENT, RestClientConstants.JSON_CONTENT_TYPE);
        headerMap.put(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT);
        headerMap.put(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT);
        headerMap.put(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE);

        /**
         * ****************POST Example**************************
         */
        var response = RestClient.build("http://localhost:8088/api/test",
                "savePerson",
                Method.POST,
                CallType.TYPE_JSON_BODY,
                headerMap,
                body,
                apiKey).executeCall();

        var mp = response.getMap();
        System.out.println(mp.toString());
        var content = response.getResponseContent();
        System.out.println(content);

        /**
         * ****************GET example******************
         */
        var queryParams = new HashMap<String, Object>();
        queryParams.put("id", 10001);

        response = RestClient.build("http://localhost:8088/api/test",
                "getPerson",
                Method.GET,
                CallType.TYPE_BASIC_JSON_QUERY,
                headerMap,
                queryParams,
                apiKey).executeCall();

        content = response.getResponseContent();
        System.out.println(content);

        mp = response.getMap();
        System.out.println(mp.toString());

        response = RestClient.build("http://localhost:8088/api/test",
                "getPerson",
                Method.GET,
                CallType.TYPE_BASIC_HTTP_QUERY,
                headerMap,
                queryParams,
                apiKey).executeCall();

        content = response.getResponseContent();
        System.out.println(content);

        mp = response.getMap();
        System.out.println(mp.toString());

    }

}
