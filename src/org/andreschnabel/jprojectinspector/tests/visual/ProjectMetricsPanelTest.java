package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.ProjectMetricsPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;

public class ProjectMetricsPanelTest extends VisualTest{
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "project metrics panel test";
					}

					@Override
					public void invoke() throws Exception {
						Project project = new Project("jlnr", "gosu");
						String[] headers = new String[] {"LinesOfCode", "TestLinesOfCode"};
						Double[] results = new Double[] {9000.0, 0.0};
						ProjectWithResults projWithResults = new ProjectWithResults(project, headers, results);
						frm.add(new ProjectMetricsPanel(projWithResults));
					}
				}
		};
	}
}
