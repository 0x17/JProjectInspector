package org.andreschnabel.jprojectinspector.testprevalence;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.Helpers;

public class UnitTestDetector {
	
	private final static String[] supportedLangs = new String[] { "Java", "Ruby", "C++", "C#", "JavaScript", "Python" };
	public static String[] getSupportedLangs() { return supportedLangs; }
	
	private final static String BASE_URL = "https://github.com/";
	private final static String DEST_BASE = "/tmp/";
	
	public boolean containsTest(Project p) throws Exception {
		String destPath = DEST_BASE + p.repoName;
		Helpers.system("git clone -v " + BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		boolean foundTest = traverseForTest(new File(destPath));
		//Helpers.deleteDir(new File(destPath));
		Helpers.rmDir(destPath);
		return foundTest;
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
			if(Helpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
				String srcStr = null;
				try {
					srcStr = Helpers.readEntireFile(root);
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

	private boolean isCsharpSrcTest(String srcStr, String filename) {
		return Helpers.strContainsOneOf(srcStr, "[TestFixture]", "[Test]", "[TearDown]", "[Setup]", "using csUnit");
	}

	private boolean isCppSrcTest(String srcStr, String filename) {
		return Helpers.strContainsOneOf(srcStr, "CPPUNIT_TEST", "#include <cppunit", "#include<cppunit", "ASSERT_THAT", "EXPECT_THAT");
	}

	private boolean isJavaScriptSrcTest(String srcStr, String filename) {
		Pattern p = Pattern.compile("expect(.+).toBe(.+);");
		Matcher m = p.matcher(srcStr);
		if(m.matches()) return true;
		return Helpers.strContainsOneOf(srcStr, "assertEqual", "registerTestSuite", "strictEqual", "deepEqual", "qunit.js", "expectEq", "expectCall", "buster.js", "buster.");
	}

	private boolean isPythonSrcTest(String srcStr, String filename) {
		return Helpers.strContainsOneOf(srcStr, "import doctest", "import unittest", "from unittest", "TestCase", "assertEqual");
	}

	private boolean isRubySrcTest(String srcStr, String filename) {
		if(filename.endsWith("_spec.rb")) return true;
		return Helpers.strContainsOneOf(srcStr, "cucumber", "assertEqual", "require 'test/unit'", "require \"test/unit\"", "Test::Unit", "require \"shoulda\"", "require 'shoulda'", "describe");
	}

	private boolean isJavaSrcTest(String srcStr, String filename) {
		return Helpers.strContainsOneOf(srcStr, "cucumber", "@Test", "org.junit", "assertEqual");
	}

}
