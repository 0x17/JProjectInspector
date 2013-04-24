package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class FreeChartPanelTest extends VisualTest {

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
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "barChart";
					}

					@Override
					public void invoke() throws Exception {
						JFreeChart chart = createBarChart();
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
						//FreeChartExporter.saveChartToPDF(createBarChart(), "testfreechart.pdf", dim.width, dim.height);
					}
				}
		};
	}
}
