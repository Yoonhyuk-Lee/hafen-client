package gugiman.automation;

import gugiman.automation.worker.Worker;
import haven.Loading;
import haven.Widget;

import java.util.*;
import java.util.concurrent.*;

public class Orchestrator extends Widget
{
    private final LinkedList<Worker> done_workers = new LinkedList<>();
    private final ConcurrentLinkedQueue<Worker> workers = new ConcurrentLinkedQueue<>();

    public void resetWorkers()
    {
	synchronized (workers)
	{
	    if(workers.size() > 0)
	    {
		for (final Worker job : workers)
		{
		    job.notify();
		}

		workers.clear();
	    }
	}
    }

    public Orchestrator() {
    }

    @Override
    public void tick(double dt)
    {
	super.tick(dt);

	synchronized (workers)
	{
	    for(final Worker worker : workers)
	    {
		try
		{
		    if(worker.check())
		    {
			synchronized (worker)
			{
			    worker.notify();
			}

			done_workers.add(worker);
		    }
		}
		catch (Loading e) {}
	    }
	    workers.removeAll(done_workers);
	    done_workers.clear();
	}
    }

    public void addWorker(final Worker worker) throws InterruptedException
    {
	if(!worker.check())
	{
	    synchronized (workers)
	    {
		workers.add(worker);
	    }

	    synchronized (worker)
	    {
		try
		{
		    worker.wait();
		}
		catch (InterruptedException e)
		{
		    synchronized (workers)
		    {
			workers.remove(worker);
			throw e;
		    }
		}

	    }
	}
    }

    @Override
    public void dispose() {
	super.dispose();
    }

    @Override
    public String toString() {
	StringBuilder res = new StringBuilder();
	synchronized (workers) {
	    for (Worker worker : workers) {
		res.append(worker.toString() + " || ");
	    }
	}
	return res.toString();
    }
}
