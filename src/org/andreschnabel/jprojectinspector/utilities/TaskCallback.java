package org.andreschnabel.jprojectinspector.utilities;

public interface TaskCallback<T> {

	public void onFinished(T result);

}
