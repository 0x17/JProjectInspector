package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.churn.AverageChurnPerRevision;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.Cpd;
import org.andreschnabel.jprojectinspector.metrics.code.LocPerSourceFile;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.Pmd;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.*;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.smells.JavaTestSmellDetector;
import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.metrics.project.Revisions;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOffline;
import org.andreschnabel.jprojectinspector.metrics.project.TestCommitComments;
import org.andreschnabel.jprojectinspector.metrics.test.TestContributors;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OfflineMetrics {
	public static Map<String, OfflineMetric> init() throws Exception {
		List<OfflineMetric> ms = new LinkedList<OfflineMetric>();
		ms.add(new Cloc());
		ms.add(new TestLinesOfCode());
		ms.add(new RoughFunctionCoverage());
		ms.add(new TestCommitComments());
		ms.add(new UnitTestDetector());
		ms.add(new ContributorsOffline());
		ms.add(new Revisions());
		ms.add(new LocPerSourceFile());
		ms.add(new Pmd());
		ms.add(new Cpd());
		ms.add(new AverageChurnPerRevision());
		initJavaSpecificOfflineMetrics(ms);

		ms.addAll(MetricPlugins.loadOfflineMetricPlugins(new File("plugins")));

		Transform<OfflineMetric, String> metricToName = new Transform<OfflineMetric, String>() {
			@Override
			public String invoke(OfflineMetric m) {
				return m.getName();
			}
		};
		List<String> names = Func.map(metricToName, ms);
		return Func.zipMap(names, ms);
	}

	private static void initJavaSpecificOfflineMetrics(List<OfflineMetric> ms) {
		ms.add(new JavaClassCoupling());
		ms.add(new JavaLinesOfCode());
		ms.add(new TestContributors());
		ms.add(new JavaTestFrameworkDetector());
		ms.add(new JavaTestLinesOfCode());
		ms.add(new SimpleJavaTestCoverage());
		ms.add(new JavaTestSmellDetector());
	}
}
