package org.codehaus.jackson.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JsonParserSequence extends JsonParserDelegate
{
  protected int _nextParser;
  protected final JsonParser[] _parsers;

  protected JsonParserSequence(JsonParser[] paramArrayOfJsonParser)
  {
    super(paramArrayOfJsonParser[0]);
    this._parsers = paramArrayOfJsonParser;
    this._nextParser = 1;
  }

  public static JsonParserSequence createFlattened(JsonParser paramJsonParser1, JsonParser paramJsonParser2)
  {
    if ((!(paramJsonParser1 instanceof JsonParserSequence)) && (!(paramJsonParser2 instanceof JsonParserSequence)))
      return new JsonParserSequence(new JsonParser[] { paramJsonParser1, paramJsonParser2 });
    ArrayList localArrayList = new ArrayList();
    if ((paramJsonParser1 instanceof JsonParserSequence))
    {
      ((JsonParserSequence)paramJsonParser1).addFlattenedActiveParsers(localArrayList);
      if (!(paramJsonParser2 instanceof JsonParserSequence))
        break label103;
      ((JsonParserSequence)paramJsonParser2).addFlattenedActiveParsers(localArrayList);
    }
    while (true)
    {
      return new JsonParserSequence((JsonParser[])localArrayList.toArray(new JsonParser[localArrayList.size()]));
      localArrayList.add(paramJsonParser1);
      break;
      label103: localArrayList.add(paramJsonParser2);
    }
  }

  protected void addFlattenedActiveParsers(List<JsonParser> paramList)
  {
    int i = -1 + this._nextParser;
    int j = this._parsers.length;
    if (i < j)
    {
      JsonParser localJsonParser = this._parsers[i];
      if ((localJsonParser instanceof JsonParserSequence))
        ((JsonParserSequence)localJsonParser).addFlattenedActiveParsers(paramList);
      while (true)
      {
        i++;
        break;
        paramList.add(localJsonParser);
      }
    }
  }

  public void close()
    throws IOException
  {
    do
      this.delegate.close();
    while (switchToNext());
  }

  public int containedParsersCount()
  {
    return this._parsers.length;
  }

  public JsonToken nextToken()
    throws IOException, JsonParseException
  {
    JsonToken localJsonToken1 = this.delegate.nextToken();
    if (localJsonToken1 != null)
      return localJsonToken1;
    while (switchToNext())
    {
      JsonToken localJsonToken2 = this.delegate.nextToken();
      if (localJsonToken2 != null)
        return localJsonToken2;
    }
    return null;
  }

  protected boolean switchToNext()
  {
    if (this._nextParser >= this._parsers.length)
      return false;
    JsonParser[] arrayOfJsonParser = this._parsers;
    int i = this._nextParser;
    this._nextParser = (i + 1);
    this.delegate = arrayOfJsonParser[i];
    return true;
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     org.codehaus.jackson.util.JsonParserSequence
 * JD-Core Version:    0.6.0
 */