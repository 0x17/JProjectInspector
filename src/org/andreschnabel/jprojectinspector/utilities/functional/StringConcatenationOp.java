package org.andreschnabel.jprojectinspector.utilities.functional;

public class StringConcatenationOp implements IBinaryOperator<String, String> {

	private final String infix;

	public StringConcatenationOp(String infix) {
		this.infix = infix;
	}

	public StringConcatenationOp() {
		infix = "";
	}

	@Override
	public String invoke(String a, String b) {
		return a+infix+b;
	}
}
