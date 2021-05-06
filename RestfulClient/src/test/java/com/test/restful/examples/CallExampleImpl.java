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
 
    String startDate ="2021-03-04";
    String endDate ="2021-03-010";

    super.addQueryParameter("StarDate", startDate);
    super.addQueryParameter("EndDate", endDate);
    super.addQueryParameter("APIKEY", "1234");
    super.addURLInfo("", "", Method.GET, AbstractCall.TYPE_HTTP_QUERY);
    
    //if the call is for post or put you should create a class inherits from AbstractMessage 
    super.addRequestBody(MessageBodyExampleImpl.build());

    super.addHeader("Content-Type", "");
    super.addHeader("Accept", "");
    super.addHeader("User-Agent", "");
  }
}



