package org.andreschnabel.jprojectinspector.utilities.functional;

public final class Tautology<T> implements Predicate<T> {
	@Override
	public boolean invoke(T obj) {
		return true;
	}
}