package com.restfulclient.impl;

public enum Method {

  GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE");
  
  private final String value ;
  private Method(String value ){
    this.value = value;
  }
 
  public String getMethod(){
    return value;
  }
}