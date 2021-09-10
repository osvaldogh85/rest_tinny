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
public enum BodyType {
  FORM_DATA(1), URL_FORM_ENCODED(2), NONE(3),RAW(4),BINARY(5),MULTIPART_FORM(6);
  
   private final int code;

   BodyType(int code) {
       this.code = code;
   }
    
    public int getCode() {
        return this.code;
    }
}