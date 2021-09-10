package com.restfulclient.impl;

public enum ParameterType {

  QUERY_PARAMS(1), PATH_VARIABLES(2);
  
  private final int value ;
  private ParameterType(int value ){
    this.value = value;
  }
 
  public int getName(){
    return value;
  }
}