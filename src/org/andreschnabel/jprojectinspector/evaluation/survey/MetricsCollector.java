package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;

import java.io.File;
import java.util.List;

/**
 * Collects specified metrics for a project.
 */
public class MetricsCollector {

	public static Float[] gatherMetricsForProject(List<String> metricNames, Project p) {
		Float[] results = new Float[metricNames.size()];

		Predicate<String> isOfflineMetric = new Predicate<String>() {
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
			float result = Float.NaN;

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
