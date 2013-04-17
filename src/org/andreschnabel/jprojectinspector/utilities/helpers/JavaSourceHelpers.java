package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSourceHelpers {
	
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

	public static List<String> splitMethodBodies(String srcStr) throws Exception {
		final Pattern methodDeclPattern = Pattern.compile("(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)");
		List<String> bodies = new LinkedList<String>();
		Matcher m = methodDeclPattern.matcher(srcStr);
		while(m.find()) {
			// move to first opening bracket
			int i = m.end();
			for(; i < srcStr.length() && srcStr.charAt(i) != '{'; i++) ;
			i++;
			int stackSize = 1;

			int methodBodyStart = i;

			// move forward until closing bracket found
			for(; i < srcStr.length() && stackSize > 0; i++) {
				switch(srcStr.charAt(i)) {
					case '{':
						stackSize++;
						break;
					case '}':
						stackSize--;
						break;
				}
			}

			int methodBodyEnd = i - 1;

			bodies.add(srcStr.substring(methodBodyStart, methodBodyEnd));
		}
		return bodies;
	}

	public static Map<String, String> extractFieldDeclarations(String srcStr) throws Exception {
		Map<String, String> fieldDecls = new HashMap<String, String>();
		// Remove all package declarations
		srcStr = srcStr.replaceAll("package\\s+[\\w\\.]+;", "");
		// Remove all class declarations
		srcStr = srcStr.replaceFirst("(public|private|protected)?\\s+class\\s+\\w+", "");
		// Remove all method bodies -> only variable declarations left are field declarations.
		for(String methodStr : splitMethodBodies(srcStr)) {
			srcStr = srcStr.replaceFirst(methodStr, "");
		}
		final Pattern varDeclPattern = Pattern.compile("([\\w\\.\\-]+)\\s+(\\w+)");
		Matcher m = varDeclPattern.matcher(srcStr);
		while(m.find()) {
			String type = m.group(1);
			String ident = m.group(2);
			fieldDecls.put(ident, type);
		}
		return fieldDecls;
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

}
