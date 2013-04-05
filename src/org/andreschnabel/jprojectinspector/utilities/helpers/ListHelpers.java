package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.Predicate;

import java.util.ArrayList;
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

	public static List<Integer> range(int n) {
		List<Integer> nums = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++) {
			nums.add(i);
		}
		return nums;
	}

	public static List<Integer> countUpTo(int n) {
		List<Integer> nums = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++) {
			nums.add((i+1));
		}
		return nums;
	}

}
