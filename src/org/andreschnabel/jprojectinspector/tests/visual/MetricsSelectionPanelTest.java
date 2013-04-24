package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.MetricsSelectionPanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;

public class MetricsSelectionPanelTest extends VisualTest{
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "metrics selection panel.";
					}

					@Override
					public void invoke() throws Exception {
						frm.add(new MetricsSelectionPanel(null));
					}
				}

		};
	}
}
