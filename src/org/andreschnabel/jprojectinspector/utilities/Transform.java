package org.andreschnabel.jprojectinspector.utilities;

public interface Transform<T, U> {

	public U invoke(T obj);

}
