package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.UserStatsPanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;

public class UserStatsPanelTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
			new VisualTestCallback() {
				@Override
				public String getDescription() {
					return "user stats panel";
				}

				@Override
				public void invoke() throws Exception {
					frm.add(new UserStatsPanel("0x17"));
					frm.setSize(300, 300);
				}
			}
		};
	}
}
