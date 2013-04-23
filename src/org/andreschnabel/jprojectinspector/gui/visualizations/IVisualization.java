package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.JFreeChart;

import java.util.Map;

public interface IVisualization {
	public String getName();
	public JFreeChart visualize(String metricName, Map<Project, Double> results);
}
