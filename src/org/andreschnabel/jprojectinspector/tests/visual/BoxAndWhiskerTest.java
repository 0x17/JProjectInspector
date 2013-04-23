package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.visualizations.BoxAndWhisker;
import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.ITestCallback;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoxAndWhiskerTest extends VisualTest {
	@Override
	protected ITestCallback[] getTests() {
		return new ITestCallback[] {
				new ITestCallback() {
					@Override
					public String getDescription() {
						return "box and whisker test";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						Map<Project, Double> projToResults = new HashMap<Project, Double>();
						projToResults.put(new Project("owner1", "repo1"), 4.0);
						projToResults.put(new Project("owner2", "repo2"), 2.0);
						JFreeChart chart = new BoxAndWhisker().visualize("loc", projToResults);
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
						waitForFrameToClose(frm);
					}
				}
		};
	}
}
