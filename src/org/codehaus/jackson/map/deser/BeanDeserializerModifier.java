package org.codehaus.jackson.map.deser;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;

public abstract class BeanDeserializerModifier
{
  public JsonDeserializer<?> modifyDeserializer(DeserializationConfig paramDeserializationConfig, BasicBeanDescription paramBasicBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
  {
    return paramJsonDeserializer;
  }

  public BeanDeserializerBuilder updateBuilder(DeserializationConfig paramDeserializationConfig, BasicBeanDescription paramBasicBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
  {
    return paramBeanDeserializerBuilder;
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     org.codehaus.jackson.map.deser.BeanDeserializerModifier
 * JD-Core Version:    0.6.0
 */