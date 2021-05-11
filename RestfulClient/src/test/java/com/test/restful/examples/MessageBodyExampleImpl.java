package com.test.restful.examples;


import com.restfulclient.impl.AbstractMessage;
import com.restfulclient.interfaces.IMessage;

/**
 *
 * @author odge
 */
public class MessageBodyExampleImpl extends AbstractMessage{

  private MessageBodyExampleImpl() {
    super();
  }

  public static IMessage build()  {
    return new MessageBodyExampleImpl();
  }
  
  @Override
  public String buildMessage() {
    String json="{\n" +
                "  \"name\": \"Jhon Doiron\",\n" +
                "  \"location\": \"Canada\",\n"  +
                "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";
    return json;
 }    
}
