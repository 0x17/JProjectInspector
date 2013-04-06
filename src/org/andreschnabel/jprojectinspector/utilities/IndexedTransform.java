package org.andreschnabel.jprojectinspector.utilities;

public interface IndexedTransform<T, U> {

	public U invoke(int i, T obj);

}
