package com.restfulclient.interfaces;

public interface IResponse {
  public IResponseResult getIResponseResult();
  public void process(IClient client);
}