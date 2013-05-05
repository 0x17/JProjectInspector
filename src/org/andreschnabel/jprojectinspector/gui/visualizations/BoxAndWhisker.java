package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.StatisticHelpers;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
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
		/*BoxAndWhiskerItem bwi = boxAndWhiskersItemFromMetricResults(results);
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		dataset.add(bwi, "test", "test2");*/

		BoxAndWhiskerCategoryDataset dataset = datasetFromResults(metricName, results);

		CategoryAxis xAxis = new CategoryAxis("Boxplot");
		NumberAxis yAxis = new NumberAxis(metricName + " values");
		yAxis.setAutoRangeIncludesZero(false);
		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		renderer.setMeanVisible(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

		return new JFreeChart(metricName, plot);

		//JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(metricName, "Project", metricName + " values", dataset, true);
		//return chart;
	}

	private BoxAndWhiskerCategoryDataset datasetFromResults(String metricName, Map<Project, Double> results) {
		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		List list = new ArrayList();
		list.addAll(results.values());
		dataset.add(list, metricName, metricName);
		return dataset;
	}

	private static BoxAndWhiskerItem boxAndWhiskersItemFromMetricResults(Map<Project, Double> results) {
		List<Double> outliers = new LinkedList<Double>();

		List<Double> orderedResults = new ArrayList<Double>(results.values().size());
		orderedResults.addAll(results.values());
		Collections.sort(orderedResults);

		Double mean = StatisticHelpers.mean(orderedResults);
		Double median = StatisticHelpers.medianOfSorted(orderedResults);
		Double q1 = StatisticHelpers.lowerQuartileOfSorted(orderedResults);
		Double q3 = StatisticHelpers.upperQuartileOfSorted(orderedResults);

		Double minOutlier = StatisticHelpers.minOfSorted(orderedResults);
		Double maxOutlier = StatisticHelpers.maxOfSorted(orderedResults);

		Double minReg = q1;
		Double maxReg = q3;

		outliers.add(minOutlier);
		outliers.add(maxOutlier);

		return new BoxAndWhiskerItem(mean, median, q1, q3, minReg, maxReg, minOutlier, maxOutlier, outliers);
	}

	/**
	 * Erzeuge Box-Plot-Diagramm für alle Metriken kombiniert und Resultate zu Projekten.
	 * @param results Map von Projekt auf Meßergebnisse der Metriken des Projekts.
	 * @return JFreeChart-Diagramm.
	 */
	public JFreeChart visualizeCombined(String[] metricNames, Map<Project, Double[]> results) {
		/*BoxAndWhiskerItem[] bwcItems = boxAndWhiskersItemArrayFromMetricsResults(results);
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		int i=0;
		for (BoxAndWhiskerItem bwcItem : bwcItems) {
			dataset.add(bwcItem, metricNames[i], metricNames[i]);
			i++;
		}

		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart("Metrics Combined", "Project", "Metrics Combined values", dataset, true);

		return chart;*/

		BoxAndWhiskerCategoryDataset dataset = datasetFromResultArrays(metricNames, results);

		CategoryAxis xAxis = new CategoryAxis("Boxplot");
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

	private static BoxAndWhiskerItem[] boxAndWhiskersItemArrayFromMetricsResults(Map<Project, Double[]> results) {
		int nmetrics = results.values().iterator().next().length;
		BoxAndWhiskerItem[] bwcItems = new BoxAndWhiskerItem[nmetrics];
		for(int i=0; i<nmetrics; i++) {
			Map<Project, Double> resultsMap = new HashMap<Project, Double>();
			for (Project project : results.keySet()) {
				resultsMap.put(project, results.get(project)[i]);
			}
			bwcItems[i] = boxAndWhiskersItemFromMetricResults(resultsMap);
		}
		return bwcItems;
	}
}
