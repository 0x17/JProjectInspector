package org.andreschnabel.jprojectinspector.metrics.test.prevalence;

import java.io.File;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

public class TestFrameworkDetector {
	
	public static final int FRAMEWORK_NONE = -1;
	public static final int FRAMEWORK_JUNIT = 0;
	public static final int FRAMEWORK_TESTNG = 1;
	public static final int FRAMEWORK_MOCKITO = 2;
	public static final int FRAMEWORK_SURE_ASSERT = 3;
	
	public static int checkForFramework(File pf) throws Exception {
		return traverseForFramework(pf);
	}
	
	private static int traverseForFramework(File pf) throws Exception {
		if(pf.isDirectory()) {
			int maxResult = -1;
			for(File f : pf.listFiles()) {
				int result = traverseForFramework(f);
				if(result > maxResult)
					maxResult = result;
			}
			return maxResult;
		}
		else {
			if(pf.getName().endsWith(".java")) {
				return checkSrcForFramework(FileHelpers.readEntireFile(pf));
			}
			else return -1;
		}
	}

	public static int checkSrcForFramework(String srcStr) {
		if(srcStr.contains("org.testng")) return FRAMEWORK_TESTNG;
		else if(srcStr.contains("org.mockito")) return FRAMEWORK_MOCKITO;
		else if(StringHelpers.containsOneOf(srcStr, "@Exemplars", "@UseCase")) return FRAMEWORK_SURE_ASSERT;
		else if(StringHelpers.containsOneOf(srcStr, "@Test", "org.junit", "assertEqual")) return FRAMEWORK_JUNIT;
		else return FRAMEWORK_NONE;
	}

}
