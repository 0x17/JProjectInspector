package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceHelpers {
	
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
		for(i = beginIndex; i<sourceStr.length() && parensCounter != 0; i++) {
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
		return getCodeOfClassInSrcStr(className, StringHelpers.removeCommentsAndStrings(sourceStr));
	}
	
	public static String getCodeOfMethodInSrcStr(String methodName, String sourceStr) throws Exception {
		String regex = "(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)[\\s\\S]*?\\{";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sourceStr);
		if(!m.find()) {
			throw new Exception("Unable to find: " + methodName + " in: " + sourceStr);
		}
		int beginIndex = m.end();
		
		// find corresponding closing bracket
		int parensCounter = 1;
		int i;
		for(i = beginIndex; i<sourceStr.length() && parensCounter != 0; i++) {
			char c = sourceStr.charAt(i);
			if(c == '{') parensCounter++;
			else if(c == '}') parensCounter--;
		}
		
		// remove trailing } from result
		i--;

		return sourceStr.substring(beginIndex, i);
	}

}
