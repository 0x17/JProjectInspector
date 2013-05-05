package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;

/**
 * Balkendiagramm.
 *
 * Gruppiert Balken für Metriken zusammen mit einer Farbe.
 * Innerhalb Gruppe enthält jedes Projekt einen Balken mit höhe Passend zur
 * Ausprägung des Metrik-Werts.
 */
public class BarChart implements IVisualization {

	@Override
	public String getName() {
		return "bar chart";
	}

	@Override
	public JFreeChart visualize(String metricName, final Map<Project, Double> results) {
		List<Project> projectsSortedByValAsc = VisualizationHelpers.sortProjectKeysByValAsc(results);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Project p : projectsSortedByValAsc) {
			dataset.setValue(results.get(p), metricName, p.toString());
		}

		JFreeChart chart = ChartFactory.createBarChart(metricName, "Projects", metricName+" values", dataset, PlotOrientation.VERTICAL, true, true, true);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

}
