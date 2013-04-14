package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.IndexedTransform;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;

import java.util.*;

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


	public static <T,U> List<U> map(Transform<T,U> transform, List<T> srcLst) {
		List<U> destLst = new ArrayList<U>(srcLst.size());
		for(T obj : srcLst) {
			destLst.add(transform.invoke(obj));
		}
		return destLst;
	}

	public static <T,U> List<U> mapi(IndexedTransform<T,U> transform, List<T> srcLst) {
		List<U> destLst = new ArrayList<U>(srcLst.size());
		for(int i=0; i<srcLst.size(); i++) {
			T obj = srcLst.get(i);
			destLst.add(transform.invoke(i, obj));
		}
		return destLst;
	}

	public static <T> List<T> filter(Predicate<T> pred, List<T> srcLst) {
		List<T> destLst = new ArrayList<T>(srcLst.size());
		for(T obj : srcLst) {
			if(pred.invoke(obj))
				destLst.add(obj);
		}
		return destLst;
	}

	public static <T> List<T> fromArray(T[] array) {
		return Arrays.asList(array);
	}

	public static <T> int count(Predicate<T> pred, List<T> lst) {
		int n = 0;
		for(T obj : lst) {
			if(pred.invoke(obj))
				n++;
		}
		return n;
	}

	public static <T> T find(Predicate<T> pred, List<T> lst) {
		for(T obj : lst) {
			if(pred.invoke(obj))
				return obj;
		}
		return null;
	}

	public static <T,U> U reduce(BinaryOperator<T,U> op, U accum, List<T> srcLst) {
		for(T obj : srcLst) {
			accum = op.invoke(accum, obj);
		}
		return accum;
	}

	public static <T> List<T> remDups(List<T> lst) {
		List<T> copy = new ArrayList<T>();
		for(T obj : lst) {
			ListHelpers.addNoDups(copy, obj);
		}
		return copy;
	}

	public static <T,U> Map<T, U> zipMap(List<T> keys, List<U> vals) {
		Map<T, U> mapping = new HashMap<T, U>();
		for(int i = 0; i < keys.size(); i++) {
			T key = keys.get(i);
			mapping.put(key, vals.get(i));
		}
		return mapping;
	}

	public static <T> void removeAll(Predicate<T> pred, List<T> lst) {
		List<T> toRem = ListHelpers.filter(pred, lst);
		lst.removeAll(toRem);
	}
}
