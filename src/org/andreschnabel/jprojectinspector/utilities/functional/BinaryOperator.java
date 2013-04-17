package org.andreschnabel.jprojectinspector.utilities.functional;

public interface BinaryOperator<T, U> {

	public U invoke(U a, T b);

}
