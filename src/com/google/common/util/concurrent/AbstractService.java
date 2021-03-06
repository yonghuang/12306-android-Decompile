package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

@Beta
public abstract class AbstractService
  implements Service
{
  private final ReentrantLock lock = new ReentrantLock();
  private final Transition shutdown = new Transition(null);
  private boolean shutdownWhenStartupFinishes = false;
  private final Transition startup = new Transition(null);
  private Service.State state = Service.State.NEW;

  protected abstract void doStart();

  protected abstract void doStop();

  public final boolean isRunning()
  {
    return state() == Service.State.RUNNING;
  }

  protected final void notifyFailed(Throwable paramThrowable)
  {
    Preconditions.checkNotNull(paramThrowable);
    this.lock.lock();
    try
    {
      if (this.state == Service.State.STARTING)
      {
        this.startup.setException(paramThrowable);
        this.shutdown.setException(new Exception("Service failed to start.", paramThrowable));
      }
      while (true)
      {
        this.state = Service.State.FAILED;
        return;
        if (this.state != Service.State.STOPPING)
          continue;
        this.shutdown.setException(paramThrowable);
      }
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  protected final void notifyStarted()
  {
    this.lock.lock();
    try
    {
      if (this.state != Service.State.STARTING)
      {
        IllegalStateException localIllegalStateException = new IllegalStateException("Cannot notifyStarted() when the service is " + this.state);
        notifyFailed(localIllegalStateException);
        throw localIllegalStateException;
      }
    }
    finally
    {
      this.lock.unlock();
    }
    this.state = Service.State.RUNNING;
    if (this.shutdownWhenStartupFinishes)
      stop();
    while (true)
    {
      this.lock.unlock();
      return;
      this.startup.set(Service.State.RUNNING);
    }
  }

  protected final void notifyStopped()
  {
    this.lock.lock();
    try
    {
      if ((this.state != Service.State.STOPPING) && (this.state != Service.State.RUNNING))
      {
        IllegalStateException localIllegalStateException = new IllegalStateException("Cannot notifyStopped() when the service is " + this.state);
        notifyFailed(localIllegalStateException);
        throw localIllegalStateException;
      }
    }
    finally
    {
      this.lock.unlock();
    }
    this.state = Service.State.TERMINATED;
    this.shutdown.set(Service.State.TERMINATED);
    this.lock.unlock();
  }

  public final ListenableFuture<Service.State> start()
  {
    this.lock.lock();
    try
    {
      if (this.state == Service.State.NEW)
      {
        this.state = Service.State.STARTING;
        doStart();
      }
      return this.startup;
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        notifyFailed(localThrowable);
        this.lock.unlock();
      }
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public Service.State startAndWait()
  {
    try
    {
      Service.State localState = (Service.State)Futures.makeUninterruptible(start()).get();
      return localState;
    }
    catch (ExecutionException localExecutionException)
    {
    }
    throw Throwables.propagate(localExecutionException.getCause());
  }

  public final Service.State state()
  {
    this.lock.lock();
    try
    {
      if ((this.shutdownWhenStartupFinishes) && (this.state == Service.State.STARTING))
      {
        Service.State localState2 = Service.State.STOPPING;
        return localState2;
      }
      Service.State localState1 = this.state;
      return localState1;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public final ListenableFuture<Service.State> stop()
  {
    this.lock.lock();
    try
    {
      if (this.state == Service.State.NEW)
      {
        this.state = Service.State.TERMINATED;
        this.startup.set(Service.State.TERMINATED);
        this.shutdown.set(Service.State.TERMINATED);
      }
      while (true)
      {
        return this.shutdown;
        if (this.state != Service.State.STARTING)
          break;
        this.shutdownWhenStartupFinishes = true;
        this.startup.set(Service.State.STOPPING);
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        notifyFailed(localThrowable);
        this.lock.unlock();
        continue;
        if (this.state != Service.State.RUNNING)
          continue;
        this.state = Service.State.STOPPING;
        doStop();
      }
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public Service.State stopAndWait()
  {
    try
    {
      Service.State localState = (Service.State)Futures.makeUninterruptible(stop()).get();
      return localState;
    }
    catch (ExecutionException localExecutionException)
    {
    }
    throw Throwables.propagate(localExecutionException.getCause());
  }

  public String toString()
  {
    return getClass().getSimpleName() + " [" + state() + "]";
  }

  private class Transition extends AbstractListenableFuture<Service.State>
  {
    private Transition()
    {
    }

    public Service.State get(long paramLong, TimeUnit paramTimeUnit)
      throws InterruptedException, TimeoutException, ExecutionException
    {
      try
      {
        Service.State localState = (Service.State)super.get(paramLong, paramTimeUnit);
        return localState;
      }
      catch (TimeoutException localTimeoutException)
      {
      }
      throw new TimeoutException(AbstractService.this.toString());
    }
  }
}

/* Location:           D:\开发工具\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.AbstractService
 * JD-Core Version:    0.6.0
 */