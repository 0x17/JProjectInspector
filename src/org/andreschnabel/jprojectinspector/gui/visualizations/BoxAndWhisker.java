package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
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

		Number mean = calcMean(results);
		Number median = calcMedian(results);
		Number q1 = calcLowerQuartile((Double)median, results);
		Number q3 = calcHigherQuartile((Double)median, results);

		Number minOutlier = calcMin(results);
		Number maxOutlier = calcMax(results);

		Number minReg = q1;
		Number maxReg = q3;

		BoxAndWhiskerItem bwi = new BoxAndWhiskerItem(mean, median, q1, q3, minReg, maxReg, minOutlier, maxOutlier, outliers);

		dataset.add(bwi, "test", "test2");

		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(metricName, "Project", metricName + " values", dataset, true);
		return chart;
	}

	private Number calcMax(Map<Project, Double> results) {
		Double max = Double.MIN_VALUE;
		for(Double d : results.values()) {
			if(Double.isNaN(d)) {
				continue;
			}
			if(d > max) {
				max = d;
			}
		}
		return max;
	}

	private Number calcMin(Map<Project, Double> results) {
		Double min = Double.MAX_VALUE;
		for(Double d : results.values()) {
			if(Double.isNaN(d)) {
				continue;
			}
			if(d < min) {
				min = d;
			}
		}
		return min;
	}

	private Number calcHigherQuartile(Double median, Map<Project, Double> results) {
		List<Double> asc = new LinkedList<Double>();
		for (Double d : results.values()) {
			if(Double.isNaN(d)) {
				continue;
			}
			if(d >= median) {
				asc.add(d);
			}
		}
		return median(asc);
	}

	private Number calcLowerQuartile(Double median, Map<Project, Double> results) {
		List<Double> asc = new LinkedList<Double>();
		for (Double d : results.values()) {
			if(Double.isNaN(d)) {
				continue;
			}
			if(d <= median) {
				asc.add(d);
			}
		}
		return median(asc);
	}

	private double median(List<Double> nums) {
		Collections.sort(nums);
		return nums.get((int)Math.floor(nums.size()/2.0));
	}

	private Number calcMedian(Map<Project, Double> results) {
		List<Double> asc = new ArrayList<Double>(results.size());
		asc.addAll(results.values());
		return median(asc);
	}

	private Number calcMean(Map<Project, Double> results) {
		Double sum = 0.0;
		for (Double d : results.values()) {
			if(!Double.isNaN(d)) {
				sum += d;
			}
		}
		return sum / (double)results.size();
	}
}
