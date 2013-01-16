package org.andreschnabel.jprojectinspector.model.coverage;

import java.util.Arrays;

public class Method {
	public String returnType;
	public String packIdent;
	public String clazzIdent;
	public String identifier;
	public String[] paramTypes;

	public Method(String returnType, String packIdent, String clazzIdent, String identifier, String[] paramTypes) {
		this.returnType = returnType;
		this.packIdent = packIdent;
		this.clazzIdent = clazzIdent;
		this.identifier = identifier;
		this.paramTypes = paramTypes;
	}

	@Override
	public String toString() {
		return "Method{" +
				"returnType='" + returnType + '\'' +
				", packIdent='" + packIdent + '\'' +
				", clazzIdent='" + clazzIdent + '\'' +
				", identifier='" + identifier + '\'' +
				", paramTypes=" + (paramTypes == null ? null : Arrays.asList(paramTypes)) +
				'}';
	}
}
