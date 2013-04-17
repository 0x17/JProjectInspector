package org.andreschnabel.jprojectinspector.utilities.functional;

public interface Transform<T, U> {

	public U invoke(T obj);

}
