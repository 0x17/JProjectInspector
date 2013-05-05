package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.util.ArrayList;
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
		BoxAndWhiskerCategoryDataset dataset = datasetFromResults(metricName, results);

		CategoryAxis xAxis = new CategoryAxis("Boxplot");
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis yAxis = new NumberAxis(metricName + " values");
		yAxis.setAutoRangeIncludesZero(false);
		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		renderer.setMeanVisible(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

		return new JFreeChart(metricName, plot);
	}

	private BoxAndWhiskerCategoryDataset datasetFromResults(String metricName, Map<Project, Double> results) {
		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		List list = new ArrayList();
		list.addAll(results.values());
		dataset.add(list, metricName, metricName);
		return dataset;
	}

	/**
	 * Erzeuge Box-Plot-Diagramm für alle Metriken kombiniert und Resultate zu Projekten.
	 * @param results Map von Projekt auf Meßergebnisse der Metriken des Projekts.
	 * @return JFreeChart-Diagramm.
	 */
	public JFreeChart visualizeCombined(String[] metricNames, Map<Project, Double[]> results) {
		BoxAndWhiskerCategoryDataset dataset = datasetFromResultArrays(metricNames, results);

		CategoryAxis xAxis = new CategoryAxis("Boxplot");
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis yAxis = new NumberAxis("Metric values");
		yAxis.setAutoRangeIncludesZero(false);
		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setMeanVisible(false);
		renderer.setFillBox(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

		return new JFreeChart("All metrics combined", plot);
	}

	private BoxAndWhiskerCategoryDataset datasetFromResultArrays(String[] metricNames, Map<Project, Double[]> results) {
		int nmetrics = results.values().iterator().next().length;
		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		for(int i=0; i<nmetrics; i++) {
			List vals = new ArrayList();
			for(Double[] resultArray : results.values()) {
				vals.add(resultArray[i]);
			}
			String name = metricNames[i].replace("Num", "#");
			dataset.add(vals, "Boxplot", name);
		}
		return dataset;
	}
}
