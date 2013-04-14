package org.andreschnabel.jprojectinspector.utilities;

public abstract class AsyncTask<T> {

	private final TaskCallback<T> callback;

	public AsyncTask(TaskCallback<T> callback) {
		this.callback = callback;
	}

	public abstract T doInBackground();

	public void execute() {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				callback.onFinished(doInBackground());
			}
		};
		Thread t = new Thread(runner);
		t.start();
	}
}
