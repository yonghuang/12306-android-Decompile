package org.codehaus.jackson.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;

public final class UTF32Reader extends BaseReader
{
  final boolean mBigEndian;
  int mByteCount = 0;
  int mCharCount = 0;
  char mSurrogate = '\000';

  public UTF32Reader(IOContext paramIOContext, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramIOContext, paramInputStream, paramArrayOfByte, paramInt1, paramInt2);
    this.mBigEndian = paramBoolean;
  }

  private boolean loadMore(int paramInt)
    throws IOException
  {
    this.mByteCount += this._length - paramInt;
    if (paramInt > 0)
    {
      if (this._ptr > 0)
      {
        for (int k = 0; k < paramInt; k++)
          this._buffer[k] = this._buffer[(k + this._ptr)];
        this._ptr = 0;
      }
      this._length = paramInt;
    }
    while (this._length < 4)
    {
      int j = this._in.read(this._buffer, this._length, this._buffer.length - this._length);
      if (j < 1)
      {
        if (j < 0)
        {
          freeBuffers();
          reportUnexpectedEOF(this._length, 4);
        }
        reportStrangeStream();
      }
      this._length = (j + this._length);
      continue;
      this._ptr = 0;
      int i = this._in.read(this._buffer);
      if (i < 1)
      {
        this._length = 0;
        if (i < 0)
        {
          freeBuffers();
          return false;
        }
        reportStrangeStream();
      }
      this._length = i;
    }
    return true;
  }

  private void reportInvalid(int paramInt1, int paramInt2, String paramString)
    throws IOException
  {
    int i = -1 + (this.mByteCount + this._ptr);
    int j = paramInt2 + this.mCharCount;
    throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(paramInt1) + paramString + " at char #" + j + ", byte #" + i + ")");
  }

  private void reportUnexpectedEOF(int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt1 + this.mByteCount;
    int j = this.mCharCount;
    throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + paramInt1 + ", needed " + paramInt2 + ", at char #" + j + ", byte #" + i + ")");
  }

  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this._buffer == null)
      return -1;
    if (paramInt2 < 1)
      return paramInt2;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length))
      reportBounds(paramArrayOfChar, paramInt1, paramInt2);
    int i = paramInt2 + paramInt1;
    int k;
    label64: int i1;
    int i2;
    label144: int m;
    if (this.mSurrogate != 0)
    {
      k = paramInt1 + 1;
      paramArrayOfChar[paramInt1] = this.mSurrogate;
      this.mSurrogate = '\000';
      if (k >= i)
        break label407;
      i1 = this._ptr;
      if (!this.mBigEndian)
        break label309;
      i2 = this._buffer[i1] << 24 | (0xFF & this._buffer[(i1 + 1)]) << 16 | (0xFF & this._buffer[(i1 + 2)]) << 8 | 0xFF & this._buffer[(i1 + 3)];
      this._ptr = (4 + this._ptr);
      if (i2 <= 65535)
        break label372;
      if (i2 > 1114111)
        reportInvalid(i2, k - paramInt1, "(above " + Integer.toHexString(1114111) + ") ");
      int i3 = i2 - 65536;
      m = k + 1;
      paramArrayOfChar[k] = (char)(55296 + (i3 >> 10));
      i2 = 0xDC00 | i3 & 0x3FF;
      if (m < i)
        break label376;
      this.mSurrogate = (char)i2;
    }
    while (true)
    {
      int n = m - paramInt1;
      this.mCharCount = (n + this.mCharCount);
      return n;
      int j = this._length - this._ptr;
      if ((j < 4) && (!loadMore(j)))
        break;
      k = paramInt1;
      break label64;
      label309: i2 = 0xFF & this._buffer[i1] | (0xFF & this._buffer[(i1 + 1)]) << 8 | (0xFF & this._buffer[(i1 + 2)]) << 16 | this._buffer[(i1 + 3)] << 24;
      break label144;
      label372: m = k;
      label376: k = m + 1;
      paramArrayOfChar[m] = (char)i2;
      if (this._ptr < this._length)
        break label64;
      m = k;
      continue;
      label407: m = k;
    }
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     org.codehaus.jackson.io.UTF32Reader
 * JD-Core Version:    0.6.0
 */