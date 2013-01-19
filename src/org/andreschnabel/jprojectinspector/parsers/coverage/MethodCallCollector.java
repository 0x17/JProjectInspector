package org.andreschnabel.jprojectinspector.parsers.coverage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.coverage.Method;
import org.andreschnabel.jprojectinspector.model.coverage.MethodIndex;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public class MethodCallCollector {

	private final static String methodCallRegex = "(\\w+)\\s*\\.\\s*(\\w+)\\s*\\((.*?)\\)";
	private final static Pattern methodCallPattern = Pattern.compile(methodCallRegex);

	public static List<Method> collectMethodCallsForFile(File f, MethodIndex index) throws Exception {
		String srcStr = FileHelpers.readEntireFile(f);
		return collectMethodCallsForSrcStr(srcStr, index);
	}

	public static List<Method> collectMethodCallsForSrcStr(String srcStr, MethodIndex index) throws Exception {
		List<Method> calledMethods = new LinkedList<Method>();
		Matcher m = methodCallPattern.matcher(srcStr);
		while(m.find()) {
			if(m.groupCount() == 3) {
				String prefix = m.group(1);
				String call = m.group(2);
				String params = m.group(3);
				Method method = tryFindMatch(prefix, call, params, index);
				if(method != null)
					calledMethods.add(method);
			}
		}
		return calledMethods;
	}

	public static Method tryFindMatch(String prefix, String call, String params, MethodIndex index) {
		int numParams = params.contains(",") ? params.split(",").length : 1;
		for(Method m : index.methods) {
			if(m.identifier.equals(call)) {
				if(m.paramTypes.length == numParams) { // TODO: Check if types really match
					return m;
				}
			}
		}

		return null;
	}

	public static List<Method> collectMethodCallsForTestFiles(File root, MethodIndex index) throws Exception {
		List<File> testFiles = collectTestFiles(root);
		List<Method> result = new LinkedList<Method>();
		for(File f : testFiles) {
			result.addAll(collectMethodCallsForFile(f, index));
		}
		return result;
	}

	public static List<File> collectTestFiles(File root) throws Exception {
		List<File> testFiles = new LinkedList<File>();
		traverseRecursively(root, testFiles);
		return testFiles;
	}

	private static void traverseRecursively(File root, List<File> testFiles) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles()) {
				traverseRecursively(f, testFiles);
			}
		} else {
			String filename = root.getName();
			if(filename.endsWith(".java") && UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(root), filename)) {
				testFiles.add(root);
			}
		}
	}
}
