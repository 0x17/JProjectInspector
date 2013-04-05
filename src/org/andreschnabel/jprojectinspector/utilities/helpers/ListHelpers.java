package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.Predicate;

import java.util.List;

public class ListHelpers {

	public static <T> void addNoDups(List<T> lst, T o) {
		if(!lst.contains(o)) {
			lst.add(o);
		}
	}

	public static <T> boolean contains(Predicate<T> pred, List<T> lst) {
		for(T obj : lst) {
			if(pred.invoke(obj))
				return true;
		}
		return false;
	}

}
