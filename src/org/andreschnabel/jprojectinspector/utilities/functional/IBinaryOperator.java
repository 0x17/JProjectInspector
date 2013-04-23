package org.andreschnabel.jprojectinspector.utilities.functional;

public interface IBinaryOperator<T, U> {

	public U invoke(U a, T b);

}
