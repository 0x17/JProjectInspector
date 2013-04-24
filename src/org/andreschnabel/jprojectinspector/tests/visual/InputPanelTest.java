package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.InputPanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;

public class InputPanelTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "input panel test";
					}

					@Override
					public void invoke() throws Exception {
						frm.add(new InputPanel());
					}
				}
		};
	}
}
