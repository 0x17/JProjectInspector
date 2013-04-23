package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.churn.CodeFrequency;
import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.metrics.project.*;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OnlineMetrics {
	public static Map<String, IOnlineMetric> init() throws Exception {
		List<IOnlineMetric> ms = new LinkedList<IOnlineMetric>();

		ms.add(new ContributorsOnline());
		ms.add(new Issues());
		ms.add(new FrontStats.Branches());
		ms.add(new FrontStats.Stars());
		ms.add(new FrontStats.PullRequests());
		ms.add(new FrontStats.Forks());
		ms.add(new CodeFrequency());
		ms.add(new Selectivity());
		ms.add(new RecentCommits());

		ms.addAll(MetricPlugins.loadOnlineMetricPlugins(new File("plugins")));

		ITransform<IOnlineMetric, String> metricToName = new ITransform<IOnlineMetric, String>() {
			@Override
			public String invoke(IOnlineMetric m) {
				return m.getName();
			}
		};
		List<String> names = Func.map(metricToName, ms);
		return Func.zipMap(names, ms);
	}
}
