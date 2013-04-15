package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;

public class GuiHelpersTest extends VisualTest {
	@Override
	protected TestCallback[] getTests() {
		return new TestCallback[] {
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Nimbus laf";
					}

					@Override
					public void invoke() throws Exception {
						LookAndFeel laf = UIManager.getLookAndFeel();
						GuiHelpers.setNimbusLaf();
						JFrame frm = getTestFrame();
						frm.add(new JButton("Test"));
						waitForFrameToClose(frm);
						UIManager.setLookAndFeel(laf);
					}
				}
		};
	}
}
