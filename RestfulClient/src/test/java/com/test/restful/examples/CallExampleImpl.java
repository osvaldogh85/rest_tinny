package com.test.restful.examples;

import com.restfulclient.impl.AbstractCall;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.ICall;

/**
 * Class used to extend from CPAbstractCall to implement an API restful call.
 */
class CallExampleImpl extends AbstractCall {

  private CallExampleImpl() {
    super();
  }

  public static ICall build()  {
    return new CallExampleImpl();
  }

  @Override
  public void prepareCall() {
    super.addURLInfo("http://localhost:8080/api/test/", "patchPerson", Method.PATCH, AbstractCall.TYPE_JSON_BODY);    
    //if the call is for post or put you should create a class inherits from AbstractMessage 
    super.addRequestBody(MessageBodyExampleImpl.build());
    super.addHeader("Content-Type", "application/json");
    super.addHeader("Accept", "*/*");
    super.addHeader("User-Agent", "TestUser");
    super.addHeader("Authorization", "Bearer jfdhh8383jhfjdsaf8y8uy9yasdf9asdf99fd");
  }
}



