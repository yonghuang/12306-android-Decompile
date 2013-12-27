package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public abstract class AbstractCaverphone
  implements StringEncoder
{
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (!(paramObject instanceof String))
      throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
    return encode((String)paramObject);
  }

  public boolean isEncodeEqual(String paramString1, String paramString2)
    throws EncoderException
  {
    return encode(paramString1).equals(encode(paramString2));
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.codec.language.AbstractCaverphone
 * JD-Core Version:    0.6.0
 */