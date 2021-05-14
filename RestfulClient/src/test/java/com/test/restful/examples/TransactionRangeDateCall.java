package com.test.restful.examples;

import com.restfulclient.impl.AbstractCall;
import com.restfulclient.impl.HttpAuthorizationImpl;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.IAuthorization;
import com.restfulclient.interfaces.ICall;
import java.util.Date;


/**
 * Class used to extend from CPAbstractCall to implement an API restful call.
 */
class TransactionRangeDateCall extends AbstractCall {

  private TransactionRangeDateCall() {
    super();
  }

  public static ICall build()  {
    return new TransactionRangeDateCall();
  }

  @Override
  public void prepareCall() {
  
    var startDateUtil = new Date();
    var currentDate = new Date();
    var startDate  ="";
    var endDate ="";

    super.addQueryParameter("startDate", startDate);
    super.addQueryParameter("endDate", endDate);
    super.addQueryParameter("ApiKey", "");
    super.addURLInfo("https://server.com/api/", "getDatesRange", Method.GET, AbstractCall.TYPE_HTTP_QUERY);

    super.addHeader("Content-Type", "");
    super.addHeader("Accept", "");
    super.addHeader("User-Agent", "");
  }
  
  public IAuthorization addAutentication(){
      return HttpAuthorizationImpl.build("jfdhh8383jhfjdsaf8y8uy9yasdf9asdf99fd");
  }
}