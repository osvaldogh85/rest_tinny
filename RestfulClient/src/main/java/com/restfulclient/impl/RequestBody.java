package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestBody;

public class RequestBody implements IRequestBody {

  private byte[] body;
  private RequestBody(byte[] body){
      this.body=body;
  } 
  public static IRequestBody build(byte[] body) {
    return new RequestBody(body);
  }

  @Override
  public byte[] getMessage() {
    return body;
  }

}