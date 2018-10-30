package sound;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Player;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;


public class StateHelper implements ControllerListener
{
    Player player = null;
    boolean configured = false;
    boolean realized = false;
    boolean prefetched = false;
    boolean endOfMedia = false;
    boolean failed = false;
    boolean closed = false;
    
    public StateHelper(Player p)
    {
        this.player = p;
        p.addControllerListener(this);
    }

    public boolean configure(int timeOutMillis)
    {
        long startTime = System.currentTimeMillis();
        synchronized(this)
        {
            if(this.player instanceof Processor)
            {
                ((Processor)player).configure();
            }
            else
                return false;
            while(!this.configured&&!this.failed)
            {
                try
                {
                    this.wait(timeOutMillis);
                }
                catch(InterruptedException ex)
                {
                    
                }
                if(System.currentTimeMillis()-startTime > timeOutMillis)
                    break;
            }
        }
        return this.configured;
    }
    public boolean realize(int timeOutMillis)
    {
        long startTime = System.currentTimeMillis();
        synchronized(this)
        {
            this.player.realize();
            while(!this.realized&&!this.failed)
            {
                try
                {
                    this.wait(timeOutMillis);
                }
                catch(InterruptedException ex)
                {
                    
                }
                if(System.currentTimeMillis()-startTime > timeOutMillis)
                    break;
            }
        }
        return this.realized;
    }
    
    public boolean prefetch(int timeOutMillis)
    {
        long startTime = System.currentTimeMillis();
        synchronized(this)
        {
            this.player.prefetch();
            while(!this.prefetched&&!this.failed)
            {
                try
                {
                    this.wait(timeOutMillis);
                }
                catch(InterruptedException ex)
                {
                    
                }
                if(System.currentTimeMillis()-startTime > timeOutMillis)
                    break;
            }
        }
        return this.prefetched&&!this.failed;
    }
    
    public boolean playToEndOfMedia(int timeOutMillis)
    {
        long startTime = System.currentTimeMillis();
        this.endOfMedia = false;
        synchronized(this)
        {
            this.player.start();
            while(!this.endOfMedia&&!this.failed)
            {
                try
                {
                    this.wait(timeOutMillis);
                }
                catch(InterruptedException ex)
                {
                    
                }
                if(System.currentTimeMillis()-startTime > timeOutMillis)
                    break;
            }
        }
        return this.endOfMedia&&!this.failed;
    }
    
    public void close()
    {
        synchronized(this)
        {
            this.player.close();
            while(!this.closed)
            {
                try
                {
                    this.wait(100);
                }
                catch(InterruptedException ex)
                {
                    
                }
            }
        }
        this.player.removeControllerListener(this);
    }
    
    public synchronized void controllerUpdate(ControllerEvent ce) 
    {
        // TODO Auto-generated method stub
        if(ce instanceof RealizeCompleteEvent)
        {
            this.realized = true;
        }
        else if(ce instanceof ConfigureCompleteEvent)
        {
            this.configured = true;
        }
        else if(ce instanceof EndOfMediaEvent)
        {
            this.endOfMedia = true;
        }
        else if(ce instanceof ControllerErrorEvent)
        {
            this.failed = true;
        }
        else if(ce instanceof ControllerClosedEvent)
        {
            this.closed = true;
        }
        else 
        {
            return;
        }
        this.notifyAll();
    }

}