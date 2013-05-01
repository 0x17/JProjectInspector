package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Box und whisker Diagramm.
 *
 * Zeigt je Metrik eine Box passend zur Verteilung an.
 */
public class BoxAndWhisker implements IVisualization {
	@Override
	public String getName() {
		return "box and whisker";
	}

	@Override
	public JFreeChart visualize(String metricName, Map<Project, Double> results) {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		for(Project p : results.keySet()) {
			Double res = results.get(p);
			List<Double> outliers = new LinkedList<Double>();
			outliers.add(res);
			dataset.add(new BoxAndWhiskerItem(res, res, res, res, res, res, res, res, outliers), "test", "test2");
		}
		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(metricName, "Project", metricName + " values", dataset, true);
		return chart;
	}
}
