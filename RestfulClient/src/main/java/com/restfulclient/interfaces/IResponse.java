package com.restfulclient.interfaces;

import java.util.Map;

public interface IResponse {
  public Map<String, Object> getMap();
  public void process(IClient client);
}