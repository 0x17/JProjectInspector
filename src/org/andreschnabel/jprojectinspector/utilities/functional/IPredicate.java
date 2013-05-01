package org.andreschnabel.jprojectinspector.utilities.functional;

/**
 * Prädikat.
 * @param <T> Typ des geprüften Objekts.
 */
public interface IPredicate<T> {

	public boolean invoke(T obj);

}
