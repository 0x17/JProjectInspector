package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;

public class JavaTestFrameworkDetector implements IOfflineMetric {
	
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

	@Override
	public String getName() {
		return "JavaTestFrameworkDetector";
	}

	@Override
	public String getDescription() {
		return "Java-specific test framework detection. -1 iff. None or unknown, 0 iff. JUnit, 1 iff. TestNG, 2. iff. Mockito, 3. iff SureAssert.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		if(JavaCommon.containsNoJavaCode(repoRoot)) {
			return Double.NaN;
		}
		return checkForFramework(repoRoot);
	}
}
