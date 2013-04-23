package org.andreschnabel.jprojectinspector.utilities.functional;

public interface IIndexedTransform<T, U> {

	public U invoke(int i, T obj);

}
