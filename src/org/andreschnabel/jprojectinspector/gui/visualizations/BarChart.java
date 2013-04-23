package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.gui.visualizations.IVisualization;
import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class BarChart implements IVisualization {

	@Override
	public String getName() {
		return "bar chart";
	}

	@Override
	public JFreeChart visualize(String metricName, Map<Project, Double> results) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Project p : results.keySet()) {
			dataset.setValue(results.get(p), metricName, p.toString());
		}
		JFreeChart chart = ChartFactory.createBarChart(metricName, "Projects", metricName+" values", dataset, PlotOrientation.VERTICAL, true, true, true);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
