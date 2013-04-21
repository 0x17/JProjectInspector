package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.freechart.MetricBarChart;
import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MetricBarChartTest extends VisualTest {
	@Override
	protected TestCallback[] getTests() {
		return new TestCallback[] {
				new TestCallback() {
					@Override
					public String getDescription() {
						return "metric bar chart test";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						Map<Project, Double[]> projToResults = new HashMap<Project, Double[]>();
						projToResults.put(new Project("owner1", "repo1"), new Double[]{1.0, 2.0, 4.0});
						projToResults.put(new Project("owner2", "repo2"), new Double[]{2.0, 8.0, 7.0});
						String[] catNames = new String[] {"loc", "testloc", "ncommits"};
						JFreeChart chart = MetricBarChart.newInstance("loc", projToResults, 0);
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
						waitForFrameToClose(frm);
					}
				}
		};
	}
}
