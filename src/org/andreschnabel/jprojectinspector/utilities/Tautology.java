package org.andreschnabel.jprojectinspector.utilities;

public class Tautology<T> implements Predicate<T> {
	@Override
	public boolean invoke(T obj) {
		return true;
	}
}
