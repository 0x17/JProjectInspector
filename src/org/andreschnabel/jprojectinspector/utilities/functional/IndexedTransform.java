package org.andreschnabel.jprojectinspector.utilities.functional;

public interface IndexedTransform<T, U> {

	public U invoke(int i, T obj);

}
