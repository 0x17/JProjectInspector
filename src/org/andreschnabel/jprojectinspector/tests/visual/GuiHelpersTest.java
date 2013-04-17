package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;
import java.awt.*;

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
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "fillHorizontalConstraints";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						frm.setLayout(new GridBagLayout());
						JPanel pane = new JPanel();
						pane.setBackground(Color.RED);
						frm.add(pane, GuiHelpers.fillHorizontalConstraints());
						waitForFrameToClose(frm);
					}
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "fillBothConstraints";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						frm.setLayout(new GridBagLayout());
						JPanel pane = new JPanel();
						pane.setBackground(Color.RED);
						frm.add(pane, GuiHelpers.fillBothConstraints());
						waitForFrameToClose(frm);
					}
				}
		};
	}
}
