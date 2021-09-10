package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.Method;
import java.util.Map;

public interface IRequest extends IHeader{

  public void execute(IClient client )throws ApiException;
  public IRequestPath getRequestPath(); 
  @Override
  public void addHeader(String name  , String value );
  @Override
  public  Map<String,Object> getHeader();
  public void addFormMultipartParams(String name, Object value);
  public boolean useBodyRequest();
  public void addBody(IRequestBody body);
  public Method getMethod();
  @Override
  public void clean();

}