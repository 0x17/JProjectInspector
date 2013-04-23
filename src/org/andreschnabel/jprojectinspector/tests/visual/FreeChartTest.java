package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.ITestCallback;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class FreeChartTest extends VisualTest {

	private static JFreeChart createBarChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(3, "test1", "test1");
		dataset.setValue(4, "test2", "test2");
		JFreeChart chart = ChartFactory.createBarChart("Testchart", "CategoryAxisLAbel", "ValueAxisLabel", dataset, PlotOrientation.HORIZONTAL, true, true, true);
	 	CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	@Override
	protected ITestCallback[] getTests() {
		return new ITestCallback[] {
				new ITestCallback() {
					@Override
					public String getDescription() {
						return "barChart";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						JFreeChart chart = createBarChart();
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
						waitForFrameToClose(frm);
						//FreeChartExporter.saveChartToPDF(createBarChart(), "testfreechart.pdf", dim.width, dim.height);
					}
				}
		};
	}
}
