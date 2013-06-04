package org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.pecker.functional.FuncInPlace;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.andreschnabel.pecker.helpers.RegexHelpers;

import java.io.File;
import java.util.List;

/**
 * Bestimme referenzierte Methoden in Tests.
 */
public final class TestMethodReferenceCounter {

	private TestMethodReferenceCounter() {}

	public static void determineUniqueMethodsReferencedInTests(File root, List<String> testedMethodNames) throws Exception {
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

	public static void determineUniqueMethodsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		determineUniqueConstructorsReferencedInTestStr(srcStr, testedMethodNames);
		determineUniqueObjectMethodsReferencedInTestStr(srcStr, testedMethodNames);
	}

	private static void determineUniqueObjectMethodsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		List<String> calledMethods = RegexHelpers.batchMatchOneGroup("\\w+\\.(\\w+)\\(.*\\)", srcStr);
		for(String methodName : calledMethods) {
			FuncInPlace.addNoDups(testedMethodNames, methodName);
		}
	}

	public static void determineUniqueConstructorsReferencedInTestStr(String srcStr, List<String> testedMethodNames) throws Exception {
		List<String> calledConstructors = RegexHelpers.batchMatchOneGroup("new\\s+(\\w+)\\(.*\\)", srcStr);
		for(String consName : calledConstructors) {
			FuncInPlace.addNoDups(testedMethodNames, consName);
		}
	}

}
