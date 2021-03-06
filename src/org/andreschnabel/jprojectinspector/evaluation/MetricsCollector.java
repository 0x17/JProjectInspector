package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.Helpers;

import java.io.File;
import java.util.List;

/**
 * Messe bestimmte Metriken für ein Projekt.
 */
public class MetricsCollector {

	public static Double[] gatherMetricsForProject(List<String> metricNames, Project p) {
		Double[] results = new Double[metricNames.size()];

		if(!ProjectDownloader.isProjectOnline(p)) {
			for(int i=0; i<results.length; i++) {
				results[i] = Double.NaN;
			}
			return results;
		}

		IPredicate<String> isOfflineMetric = new IPredicate<String>() {
			@Override
			public boolean invoke(String metricName) {
				return MetricsRegistry.getTypeOfMetric(metricName) == MetricType.Offline;
			}
		};
		boolean hasOfflineMetric = Func.contains(isOfflineMetric, metricNames);

		File repoPath = null;
		try {
			if(hasOfflineMetric) {
				repoPath = ProjectDownloader.loadProject(p);
			}
		} catch(Exception e)  {
			e.printStackTrace();
		}

		for(int i = 0; i < metricNames.size(); i++) {
			String metricName = metricNames.get(i);
			MetricType type = MetricsRegistry.getTypeOfMetric(metricName);
			Double result = Double.NaN;

			Helpers.log("Measuring " + metricName + " for " + p);

			try {
				switch(type) {
					case Offline:
						if(repoPath != null) {
							result = MetricsRegistry.measureOfflineMetric(metricName, repoPath);
						}
						break;
					case Online:
						result = MetricsRegistry.measureOnlineMetric(metricName, p);
						break;
					case Survey:
						result = MetricsRegistry.measureSurveyMetric(metricName, p);
						break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

			results[i] = result;
		}

		if(repoPath != null) {
			try {
				ProjectDownloader.deleteProject(p);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return results;
	}
}
