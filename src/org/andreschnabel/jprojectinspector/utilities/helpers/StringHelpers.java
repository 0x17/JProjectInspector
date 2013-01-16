package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringHelpers {

	public static boolean strContainsOneOf(String str, String... candidates) {
		for(String candidate : candidates)
			if(str.contains(candidate)) return true;
		return false;
	}

	public static int countOccurencesOfWord(String str, String word) {
		int ctr = 0;
		int j = 0;
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i) == word.charAt(j)) {
				if(j == word.length() - 1)  {
					ctr++;
					j = 0;
				}
				else
					j++;
			} else {
				j = 0;
			}
		}
		return ctr;
	}

	public static int countOccurencesOfWords(String str, String[] words) {
		int sum = 0;
		for(String word : words) {
			sum += countOccurencesOfWord(str, word);
		}
		return sum;
	}

	public static String capitalize(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static boolean strEndsWithOneOf(String str, String... suffixes) {
		for(String suffix : suffixes)
			if(str.endsWith(suffix)) return true;
		
		return false;
	}

	public static boolean equalsOneOf(String str, String... candidates) {
		for(String candidate : candidates)
			if(str.equals(candidate)) return true;
		
		return false;
	}

	public static String removeAllWhitespace(String s) {
		return s.replaceAll("\\s", "");
	}

	public static String removeWhitespace(String s) {
		return s.replaceAll("[\n\t]*", "");
	}

	public static String removeComments(String srcStr) {
		return srcStr.replaceAll("/\\*[\\s\\S]*?\\*/", "").replaceAll("//.*?\n", "");
	}

	public static String removeCommentsAndStrings(String srcStr) {
		return removeStrings(removeComments(srcStr));		
	}

	public static String removeStrings(String srcStr) {
		return srcStr.replaceAll("\".*?\"", "");
	}

	public static String getFirstLine(String str) {
		int firstNewline = str.indexOf('\n');
		return str.substring(0, firstNewline);
	}
		
	public static List<String> splitMethodBodies(String srcStr) throws Exception {
		final Pattern methodDeclPattern = Pattern.compile("(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)");
		List<String> bodies = new LinkedList<String>();
		Matcher m = methodDeclPattern.matcher(srcStr);
		while(m.find()) {
			// move to first opening bracket
			int i = m.end();
			for(; i<srcStr.length() && srcStr.charAt(i) != '{'; i++);
			int stackSize = 1;
			
			int methodBodyStart = i;
			
			// move forward until closing bracket found
			for(; i<srcStr.length() && stackSize > 0; i++) {
				switch(srcStr.charAt(i)) {
				case '{':
					stackSize++;
					break;
				case '}':
					stackSize--;
					break;
				}
			}
			
			int methodBodyEnd = i;
			
			bodies.add(srcStr.substring(methodBodyStart, methodBodyEnd));
		}		
		return bodies;
	}

}
