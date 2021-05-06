package com.restfulclient.impl;

import org.json.JSONObject;
import com.restfulclient.interfaces.IMessage;
/**
 * Abstract representation of JSON Body to be written in a post or put request type
 */
public abstract class AbstractMessage implements IMessage {

  private JSONObject jsonInput;

  public AbstractMessage(){}

  /**
   * Method must create a JSON format string to be parsed
   * @return
   */
  public abstract String buildMessage();

  @Override
  public JSONObject getMessage() {
    jsonInput = new JSONObject(buildMessage());
    return jsonInput;
  }
}