package org.andreschnabel.jprojectinspector.testprevalence;

import java.io.File;

import org.andreschnabel.jprojectinspector.Helpers;

public class JUnitTestDetector {
	private final static String BASE_URL = "https://github.com/";
	private static final String DEST_BASE = "/tmp/";
	
	public boolean containsTest(Project p) throws Exception {
		String destPath = DEST_BASE + p.repoName;
		Helpers.system("git clone " + BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		boolean foundTest = traverseForTest(new File(destPath));
		Helpers.deleteDir(new File(destPath));
		return foundTest;
	}

	private boolean traverseForTest(File root) throws Exception {
		boolean found = false;
		if(root.isDirectory()) {
			for(File entry : root.listFiles()) {
				found |= traverseForTest(entry);
			}
			return found;
		} else if(root.getName().endsWith(".java")) {
				return isJavaSrcTest(root);
		} else {
			return false;
		}
	}

	private boolean isJavaSrcTest(File srcFile) throws Exception {
		String sourceStr = Helpers.readEntireFile(srcFile);
		return Helpers.strContainsOneOf(sourceStr, "@Test", "org.junit");
	}

}
