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

	private static final String SCRAPING_WARNING = "<br /><br /><b>Important note:</b> This metric uses scraping to extract data from GitHub.<br />" +
			"If GitHub changes the format of their website this metric might break.<br />" +
			"In this case please get the newest version of this tool from GitHub '0x17/JProjectInspector'.</p>";
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

	public static Double measureOnlineMetric(String metric, Project p) throws Exception {
		return onlineMetrics.get(metric).measure(p);
	}

	public static Double measureOfflineMetric(String metric, File repoPath) throws Exception {
		return offlineMetrics.get(metric).measure(repoPath);
	}


	public static double measureSurveyMetric(String metric, Project p) {
		Estimation est = surveyMetrics.get(metric).measure(p);
		switch(est) {
			case None:
			default:
				return Double.NaN;
			case Lowest:
				return -1.0;
			case Highest:
				return 1.0;
		}
	}

	public static String getDescriptionOfMetric(String metric) {
		if(onlineMetrics.containsKey(metric)) {
			String warning = "";
			OnlineMetric metricObj = onlineMetrics.get(metric);
			if(metricObj.getCategory() == OnlineMetric.Category.Scraping) {
				warning = SCRAPING_WARNING;
			}
			return metricObj.getDescription() + warning;
		} else if(offlineMetrics.containsKey(metric)) {
			return offlineMetrics.get(metric).getDescription();
		} else if(surveyMetrics.containsKey(metric)) {
			return surveyMetrics.get(metric).getDescription();
		} else {
			return null;
		}
	}

	public static OnlineMetric.Category getOnlineCategoryOfMetric(String metric) {
		if(onlineMetrics.containsKey(metric))
			return onlineMetrics.get(metric).getCategory();
		return null;
	}
}
