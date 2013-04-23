package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.gui.visualizations.IVisualization;
import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Map;

public class ScatterPlot implements IVisualization {
	@Override
	public String getName() {
		return "scatterplot";
	}

	@Override
	public JFreeChart visualize(String metricName, Map<Project, Double> results) {
		XYSeries xySeries = new XYSeries("Scatter");
		int i=0;
		for(Project p : results.keySet()) {
			xySeries.add(i++, results.get(p));
		}
		XYDataset xyDataset = new XYSeriesCollection(xySeries);
		JFreeChart chart = ChartFactory.createScatterPlot(metricName, "Projects", metricName + " values", xyDataset, PlotOrientation.VERTICAL, true, true, true);
		Plot plot = chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
