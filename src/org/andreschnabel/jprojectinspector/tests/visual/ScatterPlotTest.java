package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.gui.visualizations.ScatterPlot;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScatterPlotTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "scatter plot test";
					}

					@Override
					public void invoke() throws Exception {
						Map<Project, Double> projToResults = new HashMap<Project, Double>();
						projToResults.put(new Project("owner1", "repo1"), 4.0);
						projToResults.put(new Project("owner2", "repo2"), 2.0);
						JFreeChart chart = new ScatterPlot().visualize("loc", projToResults);
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
					}
				}
		};
	}
}
