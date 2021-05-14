/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;


import com.restfulclient.call.DeleteCall;
import com.restfulclient.call.GetCall;
import com.restfulclient.call.PatchCall;
import com.restfulclient.call.PostCall;
import com.restfulclient.interfaces.ICall;
import com.restfulclient.interfaces.IResponseResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author odge
 */
public class Test {
    public static void main(String args[]){
        /* String json="{\n" +
                "  \"name\": \"Jhon Doiron\",\n" +
                "  \"location\": \"Canada\",\n"  +
                "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";*/
        
    /*     String json="{   \n" +
"      \"id\":\"5\"\n" +
"}";*/
      Map<String,Object> e =  new HashMap<String,Object>();
      e.put("id", 10001);
    
         //.build("http://localhost:8080/api/test", , DeleteCall.TYPE_JSON_BODY , json);
        ICall call =GetCall.build("http://localhost:8080/api/test", "getPersonQuery", GetCall.TYPE_JSON_QUERY, null, e);
        //DeleteCall.build("http://localhost:8080/api/test/", "deletePerson", null, DeleteCall.TYPE_JSON_BODY, json);
        IResponseResult response = (IResponseResult) call.executeCall();
        var result = response.getMap();
        result.entrySet().forEach(param -> System.out.println(param.getKey() +" ---> "+param.getValue().toString()));    
        
        var content = response.getJSON();
        System.out.println(content);
       
    }
    
}
