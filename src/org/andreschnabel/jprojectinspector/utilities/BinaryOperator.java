package org.andreschnabel.jprojectinspector.utilities;

public interface BinaryOperator<T, U> {

	public U invoke(U a, T b);

}
