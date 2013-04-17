package org.andreschnabel.jprojectinspector.gui;

import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;

import java.util.List;

public class MetricListModel extends ListListModel {
	public MetricListModel(List<String> metricNames) {
		super(metricNames);
	}

	@Override
	public Object getElementAt(int index) {
		String metric = (String) super.getElementAt(index);
		MetricType type = MetricsRegistry.getTypeOfMetric(metric);
		String onlineExtra = "";
		if(type == MetricType.Online) {
			OnlineMetric.Category category = MetricsRegistry.getOnlineCategoryOfMetric(metric);
			onlineExtra = ", " + category.toString();
		}
		return metric + " (" + type.toString() + onlineExtra + ")";
	}
}
