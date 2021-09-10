package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import java.io.IOException;

public interface IResponse {
  public IResponseResult getIResponseResult();
  public void process(IClient client)  throws IOException, ApiException;

}