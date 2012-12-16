package org.andreschnabel.jprojectinspector.metrics.test.coverage;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.Helpers;

public class TestMethodReferenceCounter {
	
	public void determineUniqueMethodsReferencedInTests(File root, List<String> testedMethodNames) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles()) {
				determineUniqueMethodsReferencedInTests(f, testedMethodNames);
			}
		}
		else {
			if(root.getName().endsWith(".java")) {
				String srcStr = Helpers.readEntireFile(root);
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
		List<String> calledMethods = Helpers.batchMatchOneGroup("\\w+\\.(\\w+)\\(.*\\)", srcStr);
		for(String methodName : calledMethods) {
			if(!testedMethodNames.contains(methodName))
				testedMethodNames.add(methodName);
		}
	}
	
	public void determineUniqueConstructorsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		List<String> calledConstructors = Helpers.batchMatchOneGroup("new\\s+(\\w+)\\(.*\\)", srcStr);
		for(String consName : calledConstructors) {
			if(!testedMethodNames.contains(consName))
				testedMethodNames.add(consName);
		}
	}

}
