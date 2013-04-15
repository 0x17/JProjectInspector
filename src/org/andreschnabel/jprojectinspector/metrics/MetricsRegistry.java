package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.*;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.metrics.project.*;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.AsmHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetricsRegistry {

	public static Map<String, OfflineMetric> offlineMetrics;
	public static Map<String, OnlineMetric> onlineMetrics;

	static {
		try {
			initOfflineMetrics();
			initOnlineMetrics();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void initOfflineMetrics() throws Exception {
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
		List<String> names = ListHelpers.map(metricToName, ms);
		offlineMetrics = ListHelpers.zipMap(names, ms);
	}

	private static void initMetricsWithAsm(File path) throws Exception {
		List<Class<?>> offmx = AsmHelpers.findClassesImplementingInterfaceInDir(path, "OfflineMetric");
		for(Class<?> c : offmx) {
			OfflineMetric om = (OfflineMetric)c.newInstance();
			offlineMetrics.put(om.getName(), om);
		}
		List<Class<?>> onmx = AsmHelpers.findClassesImplementingInterfaceInDir(path, "OnlineMetric");
		for(Class<?> c : onmx) {
			OnlineMetric om = (OnlineMetric)c.newInstance();
			onlineMetrics.put(om.getName(), om);
		}
	}

	private static void initJavaSpecificOfflineMetrics(List<OfflineMetric> ms) {
		ms.add(new JavaAverageWMC());
		ms.add(new JavaClassCoupling());
		ms.add(new JavaLinesOfCode());
		ms.add(new JavaTestContributors());
		ms.add(new JavaTestFrameworkDetector());
		ms.add(new JavaTestLinesOfCode());
		ms.add(new SimpleJavaTestCoverage());
	}

	private static void initOnlineMetrics() throws Exception {
		List<OnlineMetric> ms = new LinkedList<OnlineMetric>();
		ms.add(new ContributorsOnline());
		ms.add(new Issues());
		ms.add(new FrontStats.Branches());
		ms.add(new FrontStats.RoughCommits());
		ms.add(new FrontStats.Issues());
		ms.add(new FrontStats.Stars());
		ms.add(new FrontStats.PullRequests());
		ms.add(new FrontStats.Forks());
		ms.add(new CodeFrequency());
		ms.add(new Selectivity());
		ms.add(new ProjectAge());
		ms.add(new RecentCommits());

		// Add more online metrics here!
		// ms.add(new MyMetric());

		ms.addAll(MetricPlugins.loadOnlineMetricPlugins(new File("plugins")));

		Transform<OnlineMetric, String> metricToName = new Transform<OnlineMetric, String>() {
			@Override
			public String invoke(OnlineMetric m) {
				return m.getName();
			}
		};
		List<String> names = ListHelpers.map(metricToName, ms);
		onlineMetrics = ListHelpers.zipMap(names, ms);
	}

	public static List<String> listAllMetrics() {
		List<String> allMetrics = new LinkedList<String>();
		allMetrics.addAll(onlineMetrics.keySet());
		allMetrics.addAll(offlineMetrics.keySet());
		return allMetrics;
	}

	public static MetricType getTypeOfMetric(String metric) {
		return onlineMetrics.containsKey(metric) ? MetricType.Online : MetricType.Offline;
	}

	public static float measureOnlineMetric(String metric, Project p) throws Exception {
		return onlineMetrics.get(metric).measure(p);
	}

	public static float measureOfflineMetric(String metric, File repoPath) throws Exception {
		return offlineMetrics.get(metric).measure(repoPath);
	}


}
