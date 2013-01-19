package org.andreschnabel.jprojectinspector.model.coverage;

import java.util.Arrays;

public class Method {
	public final String returnType;
	public final String packIdent;
	public final String clazzIdent;
	public final String identifier;
	public final String[] paramTypes;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazzIdent == null) ? 0 : clazzIdent.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((packIdent == null) ? 0 : packIdent.hashCode());
		result = prime * result + Arrays.hashCode(paramTypes);
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Method other = (Method)obj;
		if (clazzIdent == null) {
			if (other.clazzIdent != null) return false;
		} else if (!clazzIdent.equals(other.clazzIdent)) return false;
		if (identifier == null) {
			if (other.identifier != null) return false;
		} else if (!identifier.equals(other.identifier)) return false;
		if (packIdent == null) {
			if (other.packIdent != null) return false;
		} else if (!packIdent.equals(other.packIdent)) return false;
		if (!Arrays.equals(paramTypes, other.paramTypes)) return false;
		if (returnType == null) {
			if (other.returnType != null) return false;
		} else if (!returnType.equals(other.returnType)) return false;
		return true;
	}
	
	
}
