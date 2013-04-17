package org.andreschnabel.jprojectinspector.utilities.threading;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskBatch<T> {

	private final List<AsyncTask<T>> tasks;
	private volatile boolean disposed;

	public AsyncTaskBatch(int size) {
		tasks = new ArrayList<AsyncTask<T>>(size);
	}

	public synchronized void dipose() {
		disposed = true;
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
						Helpers.log("Forced stop!");
						return;
					}
					task.onFinished(task.doInBackground());
				}

			}
		};

		Thread t = new Thread(runner);
		t.start();
	}

}
