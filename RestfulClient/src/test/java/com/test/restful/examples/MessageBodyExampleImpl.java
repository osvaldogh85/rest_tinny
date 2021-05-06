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
                "  \"UserID\": \"userName\",\n" +
                "  \"Role\": \"1\" ";
    return json;
 }    
}
