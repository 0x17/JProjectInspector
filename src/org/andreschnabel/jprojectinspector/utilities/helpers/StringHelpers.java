package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.functional.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;

public final class StringHelpers {

	public static int countOccurencesOfWord(String str, String word) {
		int ctr = 0;
		int j = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == word.charAt(j)) {
				if(j == word.length() - 1) {
					ctr++;
					j = 0;
				} else
					j++;
			} else {
				j = 0;
			}
		}
		return ctr;
	}

	public static int countOccurencesOfWords(final String str, String[] words) {
		return Func.reduce(new BinaryOperator<String, Integer>() {
			@Override
			public Integer invoke(Integer accum, String word) {
				return accum + countOccurencesOfWord(str, word);
			}
		}, 0, words);
	}

	public static boolean strEndsWithOneOf(final String str, String... suffixes) {
		return Func.contains(new Predicate<String>() {
			@Override
			public boolean invoke(String suffix) {
				return str.endsWith(suffix);
			}
		}, suffixes);
	}

	public static boolean containsOneOf(final String str, String... candidates) {
		return Func.contains(new Predicate<String>() {
			@Override
			public boolean invoke(String candidate) {
				return str.contains(candidate);
			}
		}, candidates);
	}

	public static boolean equalsOneOf(final String str, final String... candidates) {
		return Func.contains(new Predicate<String>() {
			@Override
			public boolean invoke(String candidate) {
				return str.equals(candidate);
			}
		}, candidates);
	}

	public static String removeAllWhitespace(String s) {
		return s.replaceAll("\\s", "");
	}

	public static String removeWhitespace(String s) {
		return s.replaceAll("[\n\t]*", "");
	}

	public static String firstLine(String str) {
		return str.substring(0, str.indexOf('\n'));
	}

	public static String capitalizeFirstLetter(String s) {
		if(s == null) return null;
		else if(s.isEmpty()) return s;
		else {
			char firstLetter = Character.toUpperCase(s.charAt(0));
			return firstLetter + s.substring(1);
		}
	}

}
