package org.andreschnabel.jprojectinspector.metrics.javaspecific.smells;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaCommon;

import java.io.File;

public class JavaTestSmellDetector implements OfflineMetric {


	@Override
	public String getName() {
		return "JavaTestSmellDetector";
	}

	@Override
	public String getDescription() {
		return "Java-specific test smell detector. Counts weighted sum of test smells. Worse smells getting higher weights.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		if(JavaCommon.containsNoJavaCode(repoRoot)) {
			return Double.NaN;
		}
		// TODO: Implement
		return 0;
	}
}
