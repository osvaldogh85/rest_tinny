package com.restfulclient.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface IStreamManager {

  public OutputStream getRequestStream()throws Exception;
  public BufferedReader getResponseStream()throws Exception;
  public void close()throws IOException;
  public IResponse execute();
}