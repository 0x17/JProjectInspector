package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetricsRegistry {

	public static Map<String, OfflineMetric> offlineMetrics;
	public static Map<String, OnlineMetric> onlineMetrics;
	public static Map<String, SurveyMetric> surveyMetrics;

	static {
		try {
			offlineMetrics = OfflineMetrics.init();
			onlineMetrics = OnlineMetrics.init();
			surveyMetrics = SurveyMetrics.init();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> listAllMetrics() {
		List<String> allMetrics = new LinkedList<String>();
		allMetrics.addAll(onlineMetrics.keySet());
		allMetrics.addAll(offlineMetrics.keySet());
		allMetrics.addAll(surveyMetrics.keySet());
		return allMetrics;
	}

	public static MetricType getTypeOfMetric(String metric) {
		return onlineMetrics.containsKey(metric) ? MetricType.Online :
				offlineMetrics.containsKey(metric) ? MetricType.Offline : MetricType.Survey;
	}

	public static float measureOnlineMetric(String metric, Project p) throws Exception {
		return onlineMetrics.get(metric).measure(p);
	}

	public static float measureOfflineMetric(String metric, File repoPath) throws Exception {
		return offlineMetrics.get(metric).measure(repoPath);
	}


	public static float measureSurveyMetric(String metric, Project p) {
		Estimation est = surveyMetrics.get(metric).measure(p);
		switch(est) {
			case None:
			default:
				return Float.NaN;
			case Lowest:
				return -1.0f;
			case Highest:
				return 1.0f;
		}
	}

	public static String getDescriptionOfMetric(String metric) {
		if(onlineMetrics.containsKey(metric))
			return onlineMetrics.get(metric).getDescription();
		else if(offlineMetrics.containsKey(metric))
			return offlineMetrics.get(metric).getDescription();
		else if(surveyMetrics.containsKey(metric))
			return surveyMetrics.get(metric).getDescription();
		else
			return null;
	}
}
