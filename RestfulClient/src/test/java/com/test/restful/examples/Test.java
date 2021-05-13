/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;


import com.restfulclient.interfaces.ICall;
import com.restfulclient.interfaces.IResponseResult;

/**
 *
 * @author odge
 */
public class Test {
    public static void main(String args[]){
        ICall call = CallExampleImpl.build();
        IResponseResult response = (IResponseResult) call.executeCall();
        var result = response.getMap();
        result.entrySet().forEach(param -> System.out.println(param.getKey() +" ---> "+param.getValue().toString()));    
        
        var content = response.getJSON();
        System.out.println(content);
       
    }
    
}
