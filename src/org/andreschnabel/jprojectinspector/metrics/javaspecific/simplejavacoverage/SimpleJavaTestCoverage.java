package org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SimpleJavaTestCoverage implements OfflineMetric {

	public static float determineMethodCoverage(File root) throws Exception {
		List<String> projectMethodNames = new LinkedList<String>();
		List<String> testedMethodNames = new LinkedList<String>();

		UniqueMethodCounter umc = new UniqueMethodCounter();
		umc.determineUniqueMethodsInProject(root, projectMethodNames);
		int numProjMethods = projectMethodNames.size();

		TestMethodReferenceCounter tmrc = new TestMethodReferenceCounter();
		tmrc.determineUniqueMethodsReferencedInTests(root, testedMethodNames);

		// remove methods called in tests but not found in project code
		List<String> unknownMethodNames = new LinkedList<String>();
		for(String testedMethodName : testedMethodNames) {
			if(!projectMethodNames.contains(testedMethodName))
				unknownMethodNames.add(testedMethodName);
		}
		testedMethodNames.removeAll(unknownMethodNames);

		int numTestRefMethods = testedMethodNames.size();
		if(numProjMethods == 0) return 0;
		return (float) numTestRefMethods / numProjMethods;
	}


	@Override
	public String getName() {
		return "jsimplecov";
	}

	@Override
	public String getDescription() {
		return "Java-specific version of method coverage approximation. Counts declared and called methods using regular expressions.";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return determineMethodCoverage(repoRoot);
	}
}
