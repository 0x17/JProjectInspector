package org.andreschnabel.jprojectinspector.utilities.functional;

/**
 * Binäroperator.
 * @param <T> Typ von Ergebnis und erstem Operanden.
 * @param <U> Typ von zweitem Operanden.
 */
public interface IBinaryOperator<T, U> {

	public U invoke(U a, T b);

}
