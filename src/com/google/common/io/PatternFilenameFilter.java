package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Beta
public final class PatternFilenameFilter
  implements FilenameFilter
{
  private final Pattern pattern;

  public PatternFilenameFilter(String paramString)
  {
    this(Pattern.compile(paramString));
  }

  public PatternFilenameFilter(Pattern paramPattern)
  {
    this.pattern = ((Pattern)Preconditions.checkNotNull(paramPattern));
  }

  public boolean accept(File paramFile, String paramString)
  {
    return this.pattern.matcher(paramString).matches();
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.google.common.io.PatternFilenameFilter
 * JD-Core Version:    0.6.0
 */