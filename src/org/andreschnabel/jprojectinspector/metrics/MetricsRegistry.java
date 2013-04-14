package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.project.*;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetricsRegistry {

	public final static Map<String, OfflineMetric> offlineMetrics = new HashMap<String, OfflineMetric>();
	public final static Map<String, OnlineMetric> onlineMetrics = new HashMap<String, OnlineMetric>();

	static {
		initOfflineMetrics();
		initOnlineMetrics();
	}

	private static void initOfflineMetrics() {
		offlineMetrics.put("loc", new Cloc());
		offlineMetrics.put("tloc", new TestLinesOfCode());
		offlineMetrics.put("cov", new RoughFunctionCoverage());
		offlineMetrics.put("ntestcontribs", new TestContributors());
		offlineMetrics.put("containstest", new UnitTestDetector());
		offlineMetrics.put("ncontribs", new ContributorsOffline());
		offlineMetrics.put("ncommits", new Commits());
	}

	private static void initOnlineMetrics() {
		onlineMetrics.put("ncontribsonline", new ContributorsOnline());
		onlineMetrics.put("nissues", new Issues());
		onlineMetrics.put("nbranches", new FrontStats.Branches());
		onlineMetrics.put("nroughcommits", new FrontStats.RoughCommits());
		onlineMetrics.put("nissues2", new FrontStats.Issues());
		onlineMetrics.put("nstars", new FrontStats.Stars());
		onlineMetrics.put("npullreqs", new FrontStats.PullRequests());
		onlineMetrics.put("nforks", new FrontStats.Forks());
		onlineMetrics.put("codefreq", new CodeFrequency());
		onlineMetrics.put("selectivity", new Selectivity());
		onlineMetrics.put("projectage", new ProjectAge());
		onlineMetrics.put("nrecentcommits", new RecentCommits());
	}

	public static List<String> listAllMetrics() {
		List<String> allMetrics = new LinkedList<String>();
		allMetrics.addAll(onlineMetrics.keySet());
		allMetrics.addAll(offlineMetrics.keySet());
		return allMetrics;
	}

	public static float measureOnlineMetric(String metric, Project p) throws Exception {
		return onlineMetrics.get(metric).measure(p);
	}

	public static float measureOfflineMetric(String metric, File repoPath) throws Exception {
		return offlineMetrics.get(metric).measure(repoPath);
	}


}
