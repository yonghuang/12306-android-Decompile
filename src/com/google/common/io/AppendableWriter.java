package com.google.common.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

class AppendableWriter extends Writer
{
  private boolean closed;
  private final Appendable target;

  AppendableWriter(Appendable paramAppendable)
  {
    this.target = paramAppendable;
  }

  private void checkNotClosed()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Cannot write to a closed writer.");
  }

  public Writer append(char paramChar)
    throws IOException
  {
    checkNotClosed();
    this.target.append(paramChar);
    return this;
  }

  public Writer append(CharSequence paramCharSequence)
    throws IOException
  {
    checkNotClosed();
    this.target.append(paramCharSequence);
    return this;
  }

  public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
    throws IOException
  {
    checkNotClosed();
    this.target.append(paramCharSequence, paramInt1, paramInt2);
    return this;
  }

  public void close()
    throws IOException
  {
    this.closed = true;
    if ((this.target instanceof Closeable))
      ((Closeable)this.target).close();
  }

  public void flush()
    throws IOException
  {
    checkNotClosed();
    if ((this.target instanceof Flushable))
      ((Flushable)this.target).flush();
  }

  public void write(int paramInt)
    throws IOException
  {
    checkNotClosed();
    this.target.append((char)paramInt);
  }

  public void write(String paramString)
    throws IOException
  {
    checkNotClosed();
    this.target.append(paramString);
  }

  public void write(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    checkNotClosed();
    this.target.append(paramString, paramInt1, paramInt1 + paramInt2);
  }

  public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    checkNotClosed();
    this.target.append(new String(paramArrayOfChar, paramInt1, paramInt2));
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.google.common.io.AppendableWriter
 * JD-Core Version:    0.6.0
 */