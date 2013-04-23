package org.andreschnabel.jprojectinspector.utilities.functional;

import java.util.List;

public final class FuncInPlace {

	private FuncInPlace() {}

	public static <T> void removeAll(IPredicate<T> pred, List<T> lst) {
		List<T> toRem = Func.filter(pred, lst);
		lst.removeAll(toRem);
	}

	public static <T> boolean addNoDups(List<T> lst, T o) {
		if(!lst.contains(o)) {
			lst.add(o);
			return true;
		} else {
			return false;
		}
	}

}
