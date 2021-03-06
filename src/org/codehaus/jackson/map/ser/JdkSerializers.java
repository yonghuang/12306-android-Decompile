package org.codehaus.jackson.map.ser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.util.Provider;

public class JdkSerializers
  implements Provider<Map.Entry<Class<?>, Object>>
{
  public Collection<Map.Entry<Class<?>, Object>> provide()
  {
    HashMap localHashMap = new HashMap();
    ToStringSerializer localToStringSerializer = ToStringSerializer.instance;
    localHashMap.put(URL.class, localToStringSerializer);
    localHashMap.put(URI.class, localToStringSerializer);
    localHashMap.put(Currency.class, localToStringSerializer);
    localHashMap.put(UUID.class, localToStringSerializer);
    localHashMap.put(Pattern.class, localToStringSerializer);
    localHashMap.put(Locale.class, localToStringSerializer);
    localHashMap.put(Locale.class, localToStringSerializer);
    localHashMap.put(AtomicReference.class, AtomicReferenceSerializer.class);
    localHashMap.put(AtomicBoolean.class, AtomicBooleanSerializer.class);
    localHashMap.put(AtomicInteger.class, AtomicIntegerSerializer.class);
    localHashMap.put(AtomicLong.class, AtomicLongSerializer.class);
    localHashMap.put(File.class, FileSerializer.class);
    localHashMap.put(Class.class, ClassSerializer.class);
    localHashMap.put(Void.TYPE, NullSerializer.class);
    return localHashMap.entrySet();
  }

  public static final class AtomicBooleanSerializer extends ScalarSerializerBase<AtomicBoolean>
  {
    public AtomicBooleanSerializer()
    {
      super(false);
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("boolean", true);
    }

    public void serialize(AtomicBoolean paramAtomicBoolean, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeBoolean(paramAtomicBoolean.get());
    }
  }

  public static final class AtomicIntegerSerializer extends ScalarSerializerBase<AtomicInteger>
  {
    public AtomicIntegerSerializer()
    {
      super(false);
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("integer", true);
    }

    public void serialize(AtomicInteger paramAtomicInteger, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeNumber(paramAtomicInteger.get());
    }
  }

  public static final class AtomicLongSerializer extends ScalarSerializerBase<AtomicLong>
  {
    public AtomicLongSerializer()
    {
      super(false);
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("integer", true);
    }

    public void serialize(AtomicLong paramAtomicLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeNumber(paramAtomicLong.get());
    }
  }

  public static final class AtomicReferenceSerializer extends SerializerBase<AtomicReference<?>>
  {
    public AtomicReferenceSerializer()
    {
      super(false);
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("any", true);
    }

    public void serialize(AtomicReference<?> paramAtomicReference, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramSerializerProvider.defaultSerializeValue(paramAtomicReference.get(), paramJsonGenerator);
    }
  }

  public static final class ClassSerializer extends ScalarSerializerBase<Class>
  {
    public ClassSerializer()
    {
      super();
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("string", true);
    }

    public void serialize(Class paramClass, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeString(paramClass.getName());
    }
  }

  public static final class FileSerializer extends ScalarSerializerBase<File>
  {
    public FileSerializer()
    {
      super();
    }

    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("string", true);
    }

    public void serialize(File paramFile, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeString(paramFile.getAbsolutePath());
    }
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     org.codehaus.jackson.map.ser.JdkSerializers
 * JD-Core Version:    0.6.0
 */