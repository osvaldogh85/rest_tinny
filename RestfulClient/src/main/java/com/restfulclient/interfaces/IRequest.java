package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.ParameterType;
import java.util.Map;

public interface IRequest extends IHeader{

  public void execute(IClient client )throws ApiException; 
  @Override
  public void addHeader(String name  , Object value );
  @Override
  public  Map<String,Object> getHeader();
  public void addFormMultipartParams(String name, Object value);
  public boolean useBodyRequest();
  public void addBody(IRequestBody body);
  public IRequestBody getBody();
  public Method getMethod();
  @Override
  public void clean();
  public String buildXFormURLEncodedParameters() throws ApiException;
  public String getFullPath();
  public void buildPathForRequest()throws ApiException;
  public void addRequestParameterInstance(IRequestParameters parameters);
  public void addNewRequestParameter(String name, Object value);
  public void addNewPathParameter(String name, Object value);
  public boolean existsParameterType();
  public boolean compareParameterType(ParameterType type);
  public ParameterType getCurrentParameterType();
}