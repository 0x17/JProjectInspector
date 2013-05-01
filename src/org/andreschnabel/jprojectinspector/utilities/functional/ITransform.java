package org.andreschnabel.jprojectinspector.utilities.functional;

/**
 * Umwandlungsvorschrift.
 * @param <T> Definitionsmenge.
 * @param <U> Zielmenge.
 */
public interface ITransform<T, U> {

	public U invoke(T obj);

}
