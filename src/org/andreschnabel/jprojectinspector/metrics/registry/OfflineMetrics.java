package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.churn.AverageChurnPerRevision;
import org.andreschnabel.jprojectinspector.metrics.code.AvgLocPerSourceFile;
import org.andreschnabel.jprojectinspector.metrics.code.AvgLocPerTestFile;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.*;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.metrics.project.BugFixCommitMessages;
import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.metrics.project.Revisions;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOffline;
import org.andreschnabel.jprojectinspector.metrics.project.TestCommitMessages;
import org.andreschnabel.jprojectinspector.metrics.test.TestContributors;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.ITransform;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Liste aller bekannten Offline-Metriken.
 */
public class OfflineMetrics {
	public static Map<String, IOfflineMetric> init() throws Exception {
		List<IOfflineMetric> ms = new LinkedList<IOfflineMetric>();
		ms.add(new Cloc());
		ms.add(new TestLinesOfCode());
		ms.add(new RoughFunctionCoverage());
		ms.add(new TestCommitMessages());
		ms.add(new BugFixCommitMessages());
		ms.add(new UnitTestDetector());
		ms.add(new ContributorsOffline());
		ms.add(new Revisions());
		ms.add(new AvgLocPerSourceFile());
		ms.add(new AvgLocPerTestFile());
		ms.add(new Pmd());
		ms.add(new Cpd());
		ms.add(new AverageChurnPerRevision());
		ms.add(new ProjectAge());
		initJavaSpecificOfflineMetrics(ms);

		ms.addAll(MetricPlugins.loadOfflineMetricPlugins(new File("plugins")));

		ITransform<IOfflineMetric, String> metricToName = new ITransform<IOfflineMetric, String>() {
			@Override
			public String invoke(IOfflineMetric m) {
				return m.getName();
			}
		};
		List<String> names = Func.map(metricToName, ms);
		return Func.zipMap(names, ms);
	}

	private static void initJavaSpecificOfflineMetrics(List<IOfflineMetric> ms) {
		ms.add(new JavaClassCoupling());
		ms.add(new JavaLinesOfCode());
		ms.add(new TestContributors());
		ms.add(new JavaTestFrameworkDetector());
		ms.add(new JavaTestLinesOfCode());
		ms.add(new SimpleJavaTestCoverage());
	}
}
