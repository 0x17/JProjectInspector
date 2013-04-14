package org.andreschnabel.jprojectinspector.freechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class FreeChartTest {

	public static JFreeChart createBarChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(3, "test", "test");
		dataset.setValue(4, "test2", "test2");
		JFreeChart chart = ChartFactory.createBarChart("Testchart", "CategoryAxisLAbel", "ValueAxisLabel", dataset, PlotOrientation.HORIZONTAL, true, true, true);
	 	CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	public static void main(String[] args) throws Exception {
		Dimension dim = new Dimension(640, 480);
		ChartWindow cw = new ChartWindow("OS Stats 2", createBarChart(), dim);
		cw.setVisible(true);
		FreeChartPdfExporter.saveChartToPDF(createBarChart(), "testfreechart.pdf", dim.width, dim.height);
	}
}
