package org.andreschnabel.jprojectinspector.utilities.threading;

public abstract class ContinuousTask<T> {

	private volatile boolean disposed;

	public synchronized void dipose() {
		disposed = true;
	}

	public abstract void onSuccess(T result);
	public abstract T iterateInBackground();

	public void execute() {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				while(!disposed) {
					T result = iterateInBackground();
					if(result != null) {
						onSuccess(result);
					}
				}
			}
		};

		Thread t = new Thread(runner);
		t.start();
	}

}
