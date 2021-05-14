/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractMessage;
import com.restfulclient.interfaces.IMessage;

/**
 *
 * @author odge
 */
public class BodyContent extends AbstractMessage {

  private final String body;
  private BodyContent(String body) {
    super();
    this.body=body;
  }

  public static IMessage build(String body)  {
    return new BodyContent(body);
  }

  @Override
  public String buildMessage()   {
    return body;
  }
}
