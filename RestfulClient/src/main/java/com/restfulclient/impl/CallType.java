/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.impl;

/**
 *
 * @author odge
 */
public enum CallType {
  TYPE_BASIC_JSON_QUERY(1), TYPE_BASIC_HTTP_QUERY(2), TYPE_JSON_BODY(3),TYPE_BASIC_JSON_QUERY_AND_BODY(4),TYPE_BASIC_HTTP_QUERY_AND_BODY(5);

  
  private final int code;

   CallType(int code) {
       this.code = code;
   }
    
    public int getCode() {
        return this.code;
    }
}