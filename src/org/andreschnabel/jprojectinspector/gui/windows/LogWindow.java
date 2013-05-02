package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import javax.swing.*;

public class LogWindow extends AbstractWindow<LogPanel> {

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
