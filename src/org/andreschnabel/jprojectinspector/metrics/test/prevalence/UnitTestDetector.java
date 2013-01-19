package org.andreschnabel.jprojectinspector.metrics.test.prevalence;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

public class UnitTestDetector {
	
	private final static String[] supportedLangs = new String[] { "Java", "Ruby", "C++", "C#", "JavaScript", "Python" };
	public static String[] getSupportedLangs() { return supportedLangs; }

	public boolean containsTest(File pf) throws Exception {
		return traverseForTest(pf);
	}
	
	public List<File> getTestFiles(File root) throws Exception {
		List<File> testFiles = new LinkedList<File>();
		if(root.isDirectory()) {
			for(File entry : root.listFiles()) {
				testFiles.addAll(getTestFiles(entry));
			}
			return testFiles;
		} else {
			String filename = root.getName();
			if(StringHelpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
				String srcStr = null;
				try {
					srcStr = FileHelpers.readEntireFile(root);
				} catch(Exception e) { return testFiles; }				
				
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

	private boolean traverseForTest(File root) throws Exception {
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
				String srcStr = null;
				try {
					srcStr = FileHelpers.readEntireFile(root);
				} catch(Exception e) { return false; }
				
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
		return StringHelpers.strContainsOneOf(srcStr, "[TestFixture]", "[Test]", "[TearDown]", "[Setup]", "using csUnit");
	}

	public static boolean isCppSrcTest(String srcStr, String filename) {
		return StringHelpers.strContainsOneOf(srcStr, "CPPUNIT_TEST", "#include <cppunit", "#include<cppunit", "ASSERT_THAT", "EXPECT_THAT");
	}

	public static boolean isJavaScriptSrcTest(String srcStr, String filename) {
		Pattern p = Pattern.compile("expect(.+).toBe(.+);");
		Matcher m = p.matcher(srcStr);
		if(m.matches()) return true;
		return StringHelpers.strContainsOneOf(srcStr, "assertEqual", "registerTestSuite", "strictEqual", "deepEqual", "qunit.js", "expectEq", "expectCall", "buster.js", "buster.");
	}

	public static boolean isPythonSrcTest(String srcStr, String filename) {
		return StringHelpers.strContainsOneOf(srcStr, "import doctest", "import unittest", "from unittest", "TestCase", "assertEqual");
	}

	public static boolean isRubySrcTest(String srcStr, String filename) {
		if(filename.endsWith("_spec.rb")) return true;
		return StringHelpers.strContainsOneOf(srcStr, "cucumber", "assertEqual", "require 'test/unit'", "require \"test/unit\"", "Test::Unit", "require \"shoulda\"", "require 'shoulda'", "describe");
	}

	public static boolean isJavaSrcTest(String srcStr, String filename) {
		return StringHelpers.strContainsOneOf(srcStr, "cucumber", "@Test", "org.junit", "assertEqual");
	}

	public boolean containsTestAndLoad(Project project) throws Exception {
		ProjectDownloader pd = new ProjectDownloader();
		File f = pd.loadProject(project);
		boolean result = (f == null) ? false : containsTest(f);
		pd.deleteProject(project);
		return result;
	}

}
