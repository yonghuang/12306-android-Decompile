package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Supplier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMap<K, V> extends ForwardingObject
  implements Map<K, V>
{
  public void clear()
  {
    delegate().clear();
  }

  public boolean containsKey(Object paramObject)
  {
    return delegate().containsKey(paramObject);
  }

  public boolean containsValue(Object paramObject)
  {
    return delegate().containsValue(paramObject);
  }

  protected abstract Map<K, V> delegate();

  public Set<Map.Entry<K, V>> entrySet()
  {
    return delegate().entrySet();
  }

  public boolean equals(@Nullable Object paramObject)
  {
    return (paramObject == this) || (delegate().equals(paramObject));
  }

  public V get(Object paramObject)
  {
    return delegate().get(paramObject);
  }

  public int hashCode()
  {
    return delegate().hashCode();
  }

  public boolean isEmpty()
  {
    return delegate().isEmpty();
  }

  public Set<K> keySet()
  {
    return delegate().keySet();
  }

  public V put(K paramK, V paramV)
  {
    return delegate().put(paramK, paramV);
  }

  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    delegate().putAll(paramMap);
  }

  public V remove(Object paramObject)
  {
    return delegate().remove(paramObject);
  }

  public int size()
  {
    return delegate().size();
  }

  @Beta
  protected void standardClear()
  {
    Iterator localIterator = entrySet().iterator();
    while (localIterator.hasNext())
    {
      localIterator.next();
      localIterator.remove();
    }
  }

  @Beta
  protected boolean standardContainsKey(@Nullable Object paramObject)
  {
    return Maps.containsKeyImpl(this, paramObject);
  }

  @Beta
  protected boolean standardContainsValue(@Nullable Object paramObject)
  {
    return Maps.containsValueImpl(this, paramObject);
  }

  @Beta
  protected Set<Map.Entry<K, V>> standardEntrySet(Supplier<Iterator<Map.Entry<K, V>>> paramSupplier)
  {
    return Maps.entrySetImpl(this, paramSupplier);
  }

  @Beta
  protected boolean standardEquals(@Nullable Object paramObject)
  {
    return Maps.equalsImpl(this, paramObject);
  }

  @Beta
  protected int standardHashCode()
  {
    return Sets.hashCodeImpl(entrySet());
  }

  @Beta
  protected boolean standardIsEmpty()
  {
    return !entrySet().iterator().hasNext();
  }

  @Beta
  protected Set<K> standardKeySet()
  {
    return Maps.keySetImpl(this);
  }

  @Beta
  protected void standardPutAll(Map<? extends K, ? extends V> paramMap)
  {
    Maps.putAllImpl(this, paramMap);
  }

  @Beta
  protected V standardRemove(@Nullable Object paramObject)
  {
    Iterator localIterator = entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (!Objects.equal(localEntry.getKey(), paramObject))
        continue;
      Object localObject = localEntry.getValue();
      localIterator.remove();
      return localObject;
    }
    return null;
  }

  @Beta
  protected String standardToString()
  {
    return Maps.toStringImpl(this);
  }

  @Beta
  protected Collection<V> standardValues()
  {
    return Maps.valuesImpl(this);
  }

  public Collection<V> values()
  {
    return delegate().values();
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.ForwardingMap
 * JD-Core Version:    0.6.0
 */