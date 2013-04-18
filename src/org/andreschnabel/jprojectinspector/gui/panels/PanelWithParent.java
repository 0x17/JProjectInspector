package org.andreschnabel.jprojectinspector.gui.panels;

import javax.swing.*;

public abstract class PanelWithParent extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JFrame parentFrame;

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
}
