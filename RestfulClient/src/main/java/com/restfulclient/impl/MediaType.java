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
public enum MediaType {
  JSON_QUERY(1), HTTP_QUERY(2), PLAIN_BODY(3),JSON_QUERY_AND_BODY(4),HTTP_QUERY_AND_BODY(5),MULTIPART_FORM(6),BINARY(7),MULTIPART_FORM_HTTP_QUERY(8),MULTIPART_FORM_JSON_QUERY(8);

  
  private final int code;

   MediaType(int code) {
       this.code = code;
   }
    
    public int getCode() {
        return this.code;
    }
}