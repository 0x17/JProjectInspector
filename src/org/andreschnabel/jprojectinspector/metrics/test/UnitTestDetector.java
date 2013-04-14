package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitTestDetector implements OfflineMetric {

	private final static String[] supportedLangs = new String[]{"Java", "Ruby", "C++", "C#", "JavaScript", "Python"};

	public static String[] getSupportedLangs() {
		return supportedLangs;
	}

	public static boolean containsTest(File pf) throws Exception {
		return traverseForTest(pf);
	}
	
	public static int countTestFiles(File pf) throws Exception {
		return getTestFiles(pf).size();
	}

	public static boolean isTest(File f) throws Exception {
		String filename = f.getName();
		if(StringHelpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
			String srcStr;
			try {
				srcStr = FileHelpers.readEntireFile(f);
			} catch(Exception e) {
				return false;
			}

			if((filename.endsWith(".java") && isJavaSrcTest(srcStr, filename))
					|| (filename.endsWith(".rb") && isRubySrcTest(srcStr, filename))
					|| (filename.endsWith(".py") && isPythonSrcTest(srcStr, filename))
					|| (filename.endsWith(".js") && isJavaScriptSrcTest(srcStr, filename))
					|| (filename.endsWith(".cpp") && isCppSrcTest(srcStr, filename))
					|| (filename.endsWith(".cs") && isCsharpSrcTest(srcStr, filename))) {
				return true;
			}
		}
		return false;
	}

	public static List<File> getTestFiles(File root) throws Exception {
		List<File> testFiles = new LinkedList<File>();
		if(root.isDirectory()) {
			for(File entry : root.listFiles()) {
				testFiles.addAll(getTestFiles(entry));
			}
			return testFiles;
		} else {
			String filename = root.getName();
			if(StringHelpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
				String srcStr;
				try {
					srcStr = FileHelpers.readEntireFile(root);
				} catch(Exception e) {
					return testFiles;
				}

				if((filename.endsWith(".java") && isJavaSrcTest(srcStr, filename))
						|| (filename.endsWith(".rb") && isRubySrcTest(srcStr, filename))
						|| (filename.endsWith(".py") && isPythonSrcTest(srcStr, filename))
						|| (filename.endsWith(".js") && isJavaScriptSrcTest(srcStr, filename))
						|| (filename.endsWith(".cpp") && isCppSrcTest(srcStr, filename))
						|| (filename.endsWith(".cs") && isCsharpSrcTest(srcStr, filename))) {
					testFiles.add(root);
				}
			}
			return testFiles;
		}
	}

	private static boolean traverseForTest(File root) throws Exception {
		boolean found = false;
		if(root.isDirectory()) {
			for(File entry : root.listFiles()) {
				if(found) break;
				found |= traverseForTest(entry);
			}
			return found;
		} else {
			String filename = root.getName();
			if(StringHelpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
				String srcStr;
				try {
					srcStr = FileHelpers.readEntireFile(root);
				} catch(Exception e) {
					return false;
				}

				if(filename.endsWith(".java")) return isJavaSrcTest(srcStr, filename);
				else if(filename.endsWith(".rb")) return isRubySrcTest(srcStr, filename);
				else if(filename.endsWith(".py")) return isPythonSrcTest(srcStr, filename);
				else if(filename.endsWith(".js")) return isJavaScriptSrcTest(srcStr, filename);
				else if(filename.endsWith(".cpp")) return isCppSrcTest(srcStr, filename);
				else if(filename.endsWith(".cs")) return isCsharpSrcTest(srcStr, filename);
			}
			return false;
		}
	}

	public static boolean isCsharpSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "[TestFixture]", "[Test]", "[TearDown]", "[Setup]", "using csUnit");
	}

	public static boolean isCppSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "CPPUNIT_TEST", "#include <cppunit", "#include<cppunit", "ASSERT_THAT", "EXPECT_THAT");
	}

	public static boolean isJavaScriptSrcTest(String srcStr, String filename) {
		Pattern p = Pattern.compile("expect(.+).toBe(.+);");
		Matcher m = p.matcher(srcStr);
		if(m.matches()) return true;
		return StringHelpers.containsOneOf(srcStr, "assertEqual", "registerTestSuite", "strictEqual", "deepEqual", "qunit.js", "expectEq", "expectCall", "buster.js", "buster.");
	}

	public static boolean isPythonSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "import doctest", "import unittest", "from unittest", "TestCase", "assertEqual");
	}

	public static boolean isRubySrcTest(String srcStr, String filename) {
		if(filename.endsWith("_spec.rb")) return true;
		return StringHelpers.containsOneOf(srcStr, "cucumber", "assertEqual", "require 'test/unit'", "require \"test/unit\"", "Test::Unit", "require \"shoulda\"", "require 'shoulda'", "describe");
	}

	public static boolean isJavaSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "cucumber", "@Test", "org.junit", "assertEqual");
	}

	public static boolean containsTestAndLoad(Project project) throws Exception {
		File f = ProjectDownloader.loadProject(project);
		boolean result = (f == null) ? false : containsTest(f);
		ProjectDownloader.deleteProject(project);
		return result;
	}

	@Override
	public String getName() {
		return "containstest";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return containsTest(repoRoot) ? 1.0f : 0.0f;
	}
}
