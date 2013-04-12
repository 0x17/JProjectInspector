package org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

public class TestMethodReferenceCounter {

	public void determineUniqueMethodsReferencedInTests(File root, List<String> testedMethodNames) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles()) {
				determineUniqueMethodsReferencedInTests(f, testedMethodNames);
			}
		} else {
			if(root.getName().endsWith(".java")) {
				String srcStr = FileHelpers.readEntireFile(root);
				if(UnitTestDetector.isJavaSrcTest(srcStr, root.getName())) {
					determineUniqueMethodsReferencedInTestStr(srcStr, testedMethodNames);
				}
			}
		}
	}

	public void determineUniqueMethodsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		determineUniqueConstructorsReferencedInTestStr(srcStr, testedMethodNames);
		determineUniqueObjectMethodsReferencedInTestStr(srcStr, testedMethodNames);
	}

	private void determineUniqueObjectMethodsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		List<String> calledMethods = RegexHelpers.batchMatchOneGroup("\\w+\\.(\\w+)\\(.*\\)", srcStr);
		for(String methodName : calledMethods) {
			Helpers.addToLstNoDups(testedMethodNames, methodName);
		}
	}

	public void determineUniqueConstructorsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		List<String> calledConstructors = RegexHelpers.batchMatchOneGroup("new\\s+(\\w+)\\(.*\\)", srcStr);
		for(String consName : calledConstructors) {
			Helpers.addToLstNoDups(testedMethodNames, consName);
		}
	}

}
