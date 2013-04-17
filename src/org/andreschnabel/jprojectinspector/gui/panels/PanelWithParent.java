package org.andreschnabel.jprojectinspector.gui.panels;

import javax.swing.*;

public class PanelWithParent extends JPanel {

	protected JFrame parentFrame;

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
}
