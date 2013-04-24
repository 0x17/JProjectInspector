package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.MetricResultsPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;

import java.util.Arrays;
import java.util.List;

public class MetricsResultsPanelTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "metrics results panel test";
					}

					@Override
					public void invoke() throws Exception {
						List<Project> projs = Arrays.asList(new Project("0x17", "KosuFSharp"));
						frm.add(new MetricResultsPanel(projs, Arrays.asList("NumStars")));
						frm.pack();
					}
				}
		};
	}
}
