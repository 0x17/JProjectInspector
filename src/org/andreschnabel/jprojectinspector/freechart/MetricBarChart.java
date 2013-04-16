package org.andreschnabel.jprojectinspector.freechart;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class MetricBarChart {

	public static JFreeChart newInstance(String title, Map<Project, Float[]> results, int col) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Project p : results.keySet()) {
			dataset.setValue(results.get(p)[col], p.toString(), p.toString());
		}
		JFreeChart chart = ChartFactory.createBarChart(title, "Projects", title+" values", dataset, PlotOrientation.VERTICAL, true, true, true);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}



}
