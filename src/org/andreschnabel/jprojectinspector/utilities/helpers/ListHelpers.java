package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.List;

public class ListHelpers {

	public static <T> void addNoDups(List<T> lst, T o) {
		if(!lst.contains(o)) {
			lst.add(o);
		}
	}

}
