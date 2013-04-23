package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.VisualizationPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.ITestCallback;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizationPanelTest extends VisualTest {
	@Override
	protected ITestCallback[] getTests() {
		return new ITestCallback[] {
				new ITestCallback() {
					@Override
					public String getDescription() {
						return "visualization panel test";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						List<String> metricNames = Arrays.asList(new String[] {"metric1", "metric2"});
						Map<Project, Double[]> results = new HashMap<Project, Double[]>();
						results.put(new Project("owner1", "repo1"), new Double[] {1.0, 2.0});
						frm.add(new VisualizationPanel(metricNames, results));
						frm.pack();
						waitForFrameToClose(frm);
					}
				}
		};
	}
}
