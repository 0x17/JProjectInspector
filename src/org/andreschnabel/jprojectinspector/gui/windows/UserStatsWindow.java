package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.UserStatsPanel;

import javax.swing.*;

/**
 * Fenster mit Nutzerstatistiken.
 * @see UserStatsPanel
 */
public class UserStatsWindow extends AbstractWindow<UserStatsPanel> {
	private static final long serialVersionUID = 1L;

	public UserStatsWindow(String user) {
		super("Statistics for " + user, 300, 300, JFrame.DISPOSE_ON_CLOSE, new UserStatsPanel(user));
	}



}
