package org.andreschnabel.jprojectinspector.utilities.threading;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskBatch<T> {

	private final List<AsyncTask<T>> tasks;
	private volatile boolean disposed;
	private volatile boolean done;

	public AsyncTaskBatch(int size) {
		tasks = new ArrayList<AsyncTask<T>>(size);
	}

	public synchronized void dipose() {
		disposed = true;
	}

	public synchronized boolean isDone() {
		return done;
	}

	public void add(AsyncTask<T> task) {
		tasks.add(task);
	}

	public void execute() {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				for(AsyncTask<T> task : tasks) {
					if(disposed) {
						return;
					}
					task.onFinished(task.doInBackground());
				}
				done = true;
			}
		};

		Thread t = new Thread(runner);
		t.start();
	}

}
