package org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaCommon;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SimpleJavaTestCoverage implements OfflineMetric {

	public static Double determineMethodCoverage(File root) throws Exception {
		List<String> projectMethodNames = new LinkedList<String>();
		List<String> testedMethodNames = new LinkedList<String>();

		UniqueMethodCounter.determineUniqueMethodsInProject(root, projectMethodNames);
		int numProjMethods = projectMethodNames.size();

		TestMethodReferenceCounter.determineUniqueMethodsReferencedInTests(root, testedMethodNames);

		// remove methods called in tests but not found in project code
		List<String> unknownMethodNames = new LinkedList<String>();
		for(String testedMethodName : testedMethodNames) {
			if(!projectMethodNames.contains(testedMethodName))
				unknownMethodNames.add(testedMethodName);
		}
		testedMethodNames.removeAll(unknownMethodNames);

		int numTestRefMethods = testedMethodNames.size();
		if(numProjMethods == 0) return Double.NaN;
		return (double) numTestRefMethods / numProjMethods;
	}


	@Override
	public String getName() {
		return "JavaCoverageApprox";
	}

	@Override
	public String getDescription() {
		return "Java-specific version of method coverage approximation. Counts declared and called methods using regular expressions.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		if(JavaCommon.containsNoJavaCode(repoRoot)) {
			return Double.NaN;
		}
		return determineMethodCoverage(repoRoot);
	}
}
