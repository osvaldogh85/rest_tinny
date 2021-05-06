/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;


import com.restfulclient.interfaces.ICall;

/**
 *
 * @author odge
 */
public class Test {
    public static void main(String args[]){
        ICall call = CallExampleImpl.build();
        var result = call.executeCall();
        result.entrySet().forEach(param -> System.out.println(param.getValue().toString()));
    }
    
}
