package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitTestDetector implements OfflineMetric {

	private final static String[] supportedLangs = new String[]{"Java", "Ruby", "C++", "C#", "JavaScript", "Python"};

	private static class IsTestPredicate implements Predicate<File> {
		@Override
		public boolean invoke(File f) {
			try {
				return isTest(f);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public static String[] getSupportedLangs() {
		return supportedLangs;
	}

	public static boolean containsTest(File root) throws Exception {
		return Func.contains(new IsTestPredicate(), FileHelpers.filesInTree(root));
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
		return Func.filter(new IsTestPredicate(), FileHelpers.filesInTree(root));
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
		boolean result = false;
		try {
			File f = ProjectDownloader.loadProject(project);
			result = (f == null) ? false : containsTest(f);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ProjectDownloader.deleteProject(project);
		}
		return result;
	}

	@Override
	public String getName() {
		return "ContainsTest";
	}

	@Override
	public String getDescription() {
		return "Use heuristic to determine if a projects (probably) contains a test (or not). 1.0f iff. (at least one) test. 0.0f otherwise.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return containsTest(repoRoot) ? 1.0f : 0.0f;
	}
}
