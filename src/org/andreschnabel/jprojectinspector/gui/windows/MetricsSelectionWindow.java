package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.InputProjectTablePanel;
import org.andreschnabel.jprojectinspector.gui.panels.MetricsSelectionPanel;

import javax.swing.*;

public class MetricsSelectionWindow extends AbstractWindow<MetricsSelectionPanel> {

	private static final long serialVersionUID = 1L;

	public MetricsSelectionWindow(final InputProjectTablePanel projLstPanel) {
		super("Metrics Selection", 800, 600, JFrame.HIDE_ON_CLOSE, new MetricsSelectionPanel(projLstPanel));
	}

	public static void main(String[] args) {
		JFrame msw = new MetricsSelectionWindow(null);
		msw.setVisible(true);
		msw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
