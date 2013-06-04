package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.LogPanel;
import org.andreschnabel.pecker.helpers.Helpers;

import javax.swing.*;

public class LogWindow extends AbstractWindow<LogPanel> {
	private static final long serialVersionUID = 1L;
	
	private LogPanel logPanel;

	public LogWindow() {
		super("Log Window", 640, 480, JFrame.DISPOSE_ON_CLOSE, new LogPanel());
		logPanel = this.panel;
		Helpers.addLogObserver(logPanel);
	}

	@Override
	public void dispose() {
		Helpers.removeLogObserver(logPanel);
		super.dispose();
	}
}
