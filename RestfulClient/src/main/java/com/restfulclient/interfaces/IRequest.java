package com.restfulclient.interfaces;

import java.util.Map;

public interface IRequest extends IHeader{

  public IResponse call(IClient client )throws Exception;
  public void addRequestPath(IRequestPath request);
  public void addRequestBody(IRequestBody requestBody );
  public IRequestPath getRequestPath();
  public void setTimeOut(int timeout ); 
  public int getTimeOut() ;
  @Override
  public void addHeader(String name  , String value );
  @Override
  public  Map<String,Object> getHeader();
}