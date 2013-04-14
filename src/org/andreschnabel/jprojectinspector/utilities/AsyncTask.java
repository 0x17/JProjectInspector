package org.andreschnabel.jprojectinspector.utilities;

public abstract class AsyncTask<T> {

	public abstract void onFinished(T result);
	public abstract T doInBackground();

	public void execute() {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				onFinished(doInBackground());
			}
		};
		Thread t = new Thread(runner);
		t.start();
	}
}
