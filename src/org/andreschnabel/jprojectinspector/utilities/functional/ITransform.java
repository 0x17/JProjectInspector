package org.andreschnabel.jprojectinspector.utilities.functional;

public interface ITransform<T, U> {

	public U invoke(T obj);

}
