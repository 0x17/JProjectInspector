package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.*;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.metrics.project.Commits;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOffline;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsTestCommit;
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
		ms.add(new ContributorsTestCommit());
		ms.add(new UnitTestDetector());
		ms.add(new ContributorsOffline());
		ms.add(new Commits());
		initJavaSpecificOfflineMetrics(ms);

		// Add more offline metrics here!
		// ms.add(new MyMetric());

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
	}
}
