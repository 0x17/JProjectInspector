package org.andreschnabel.jprojectinspector.utilities.threading;

import javax.swing.*;

public abstract class AsyncTask<T> {

	public abstract void onFinished(T result);
	public abstract T doInBackground();

	public void execute() {
		SwingWorker<T, Object> worker = new SwingWorker<T, Object>() {
			@Override
			protected T doInBackground() throws Exception {
				return backFunc();
			}

			@Override
			protected void done() {
				super.done();
				try {
					onFinished(get());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		worker.execute();
	}

	private T backFunc() {
		return doInBackground();
	}
}
