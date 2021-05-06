package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IMessage;

public class RequestBodyImpl implements IRequestBody {

  private IMessage iMessage = null;

   
  private RequestBodyImpl( ){    
  }
  
  private RequestBodyImpl(IMessage iMessage ){
      this.iMessage=iMessage;
  } 
  
  public static IRequestBody build() {
    return new RequestBodyImpl();
  }

  public static IRequestBody build(IMessage body) {
    return new RequestBodyImpl(body);
  }

  @Override
  public IMessage getRequestBody() {
    return iMessage;
  }

    @Override
  public void setRequestBody(IMessage iMessage) {
      this.iMessage=iMessage;
  }
}