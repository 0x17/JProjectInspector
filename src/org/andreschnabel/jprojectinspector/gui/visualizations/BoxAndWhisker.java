package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.StatisticHelpers;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.util.*;

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

		List<Double> outliers = new LinkedList<Double>();

		List<Double> orderedResults = new ArrayList<Double>(results.values().size());
		orderedResults.addAll(results.values());
		Collections.sort(orderedResults);

		Number mean = StatisticHelpers.mean(orderedResults);
		Number median = StatisticHelpers.medianOfSorted(orderedResults);
		Number q1 = StatisticHelpers.lowerQuartileOfSorted(orderedResults);
		Number q3 = StatisticHelpers.upperQuartileOfSorted(orderedResults);

		Number minOutlier = StatisticHelpers.minOfSorted(orderedResults);
		Number maxOutlier = StatisticHelpers.maxOfSorted(orderedResults);

		Number minReg = q1;
		Number maxReg = q3;

		BoxAndWhiskerItem bwi = new BoxAndWhiskerItem(mean, median, q1, q3, minReg, maxReg, minOutlier, maxOutlier, outliers);

		dataset.add(bwi, "test", "test2");

		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(metricName, "Project", metricName + " values", dataset, true);
		return chart;
	}

	/**
	 * Erzeuge Visualisierung für alle Metriken kombiniert und Resultate zu Projekten.
	 * @param results Map von Projekt auf Meßergebnisse der Metriken des Projekts.
	 * @return JFreeChart-Diagramm.
	 */
	public JFreeChart visualizeCombined(Map<Project, Double[]> results) {
		return null;
	}
}
