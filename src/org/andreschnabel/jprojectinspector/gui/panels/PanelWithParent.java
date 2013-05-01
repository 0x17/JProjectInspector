package org.andreschnabel.jprojectinspector.gui.panels;

import javax.swing.*;

/**
 * Abgeleitete Klassen repräsentieren Panel mit einem umschließenden Elternframe.
 * Erlaubt Panel bei Button-Klick das Schließen seines umschließenden Frames.
 */
public abstract class PanelWithParent extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JFrame parentFrame;

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
}
