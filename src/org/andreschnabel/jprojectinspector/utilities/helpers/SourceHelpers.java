package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceHelpers {
	
	public static List<String> listClassNamesInSrcStr(String sourceStr) {
		List<String> classNames = new LinkedList<String>();
		String regex = "class\\s+(\\w+)[\\s\\S]*?\\{";
		Matcher m = Pattern.compile(regex).matcher(sourceStr);
		while(m.find()) {
			classNames.add(m.group(1));
		}
		return classNames;
	}
	
	public static List<String> listMethodNamesInSrcStr(String sourceStr) {
		List<String> methodNames = new LinkedList<String>();
		String regex = "(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)[\\s\\S]*?\\{";
		Matcher m = Pattern.compile(regex).matcher(sourceStr);
		while(m.find()) {
			methodNames.add(m.group(3));
		}
		return methodNames;
	}

	public static String getCodeOfClassInSrcStr(String className, String sourceStr) throws Exception {
		String regex = "class\\s+" + className + "[\\s\\S]*?\\{";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sourceStr);
		if(!m.find()) {
			throw new Exception("Unable to find: " + className + " in: " + sourceStr);
		}
		int beginIndex = m.end();

		// find corresponding closing bracket
		int parensCounter = 1;
		int i;
		for(i = beginIndex; i < sourceStr.length() && parensCounter != 0; i++) {
			char c = sourceStr.charAt(i);
			if(c == '{') parensCounter++;
			else if(c == '}') parensCounter--;
		}

		// remove trailing } from result
		i--;

		return sourceStr.substring(beginIndex, i);
	}

	public static String getCodeOfClassInFile(String className, File f) throws Exception {
		String sourceStr = FileHelpers.readEntireFile(f);
		return getCodeOfClassInSrcStr(className, sourceStr);
	}

	public static String getCodeOfMethodInSrcStr(String methodName, String sourceStr) throws Exception {
		String regex = "(private|public|protected)?(\\s+static)?\\s+\\w+\\s+("+methodName+")\\(.*\\)[\\s\\S]*?\\{";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sourceStr);
		if(!m.find()) {
			throw new Exception("Unable to find: " + methodName + " in: " + sourceStr);
		}
		int beginIndex = m.end();

		// find corresponding closing bracket
		int parensCounter = 1;
		int i;
		for(i = beginIndex; i < sourceStr.length() && parensCounter != 0; i++) {
			char c = sourceStr.charAt(i);
			if(c == '{') parensCounter++;
			else if(c == '}') parensCounter--;
		}

		// remove trailing } from result
		i--;

		return sourceStr.substring(beginIndex, i);
	}

}
