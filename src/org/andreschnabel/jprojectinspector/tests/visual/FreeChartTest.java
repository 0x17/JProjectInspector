package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.TestCallback;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

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
	protected TestCallback[] getTests() {
		return new TestCallback[] {
				new TestCallback() {
					@Override
					public String getDescription() {
						return "barChart";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						JFreeChart chart = createBarChart();
						ChartPanel cp = new ChartPanel(chart);
						frm.add(cp);
						frm.pack();
						waitForFrameToClose(frm);
						//FreeChartExporter.saveChartToPDF(createBarChart(), "testfreechart.pdf", dim.width, dim.height);
					}
				}
		};
	}
}
