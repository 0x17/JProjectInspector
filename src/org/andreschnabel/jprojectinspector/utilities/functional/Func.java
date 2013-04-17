package org.andreschnabel.jprojectinspector.utilities.functional;

import java.util.*;

public final class Func {

	private Func() {}

	public static <T> boolean contains(Predicate<T> pred, Iterable<T> coll) {
		for(T obj : coll) {
			if(pred.invoke(obj))
				return true;
		}
		return false;
	}

	public static <T> boolean contains(Predicate<T> pred, T[] arr) {
		return contains(pred, Arrays.asList(arr));
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

	public static <T,U> List<U> map(Transform<T,U> transform, Collection<T> coll) {
		List<U> destLst = new ArrayList<U>(coll.size());
		for(T obj : coll) {
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

	public static <T,U> Map<T, U> zipMap(List<T> keys, List<U> vals) {
		Map<T, U> mapping = new HashMap<T, U>();
		for(int i = 0; i < keys.size(); i++) {
			T key = keys.get(i);
			mapping.put(key, vals.get(i));
		}
		return mapping;
	}

	public static <T> List<T> filter(Predicate<T> pred, Collection<T> coll) {
		List<T> destLst = new ArrayList<T>(coll.size());
		for(T obj : coll) {
			if(pred.invoke(obj))
				destLst.add(obj);
		}
		return destLst;
	}

	public static <T> List<T> fromArray(T[] array) {
		return Arrays.asList(array);
	}

	public static <T> int count(Predicate<T> pred, Iterable<T> coll) {
		int n = 0;
		for(T obj : coll) {
			if(pred.invoke(obj))
				n++;
		}
		return n;
	}

	public static <T> T find(Predicate<T> pred, Iterable<T> coll) {
		for(T obj : coll) {
			if(pred.invoke(obj))
				return obj;
		}
		return null;
	}

	public static <T,U> U reduce(BinaryOperator<T,U> op, U accum, Iterable<T> coll) {
		for(T obj : coll) {
			accum = op.invoke(accum, obj);
		}
		return accum;
	}

	public static <T,U> U reduce(BinaryOperator<T,U> op, U accum, T[] arr) {
		return reduce(op, accum, Arrays.asList(arr));
	}

	public static <T> List<T> remDups(List<T> lst) {
		List<T> copy = new ArrayList<T>();
		for(T obj : lst) {
			FuncInPlace.addNoDups(copy, obj);
		}
		return copy;
	}
}