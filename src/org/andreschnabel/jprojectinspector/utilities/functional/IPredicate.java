package org.andreschnabel.jprojectinspector.utilities.functional;

public interface IPredicate<T> {

	public boolean invoke(T obj);

}
