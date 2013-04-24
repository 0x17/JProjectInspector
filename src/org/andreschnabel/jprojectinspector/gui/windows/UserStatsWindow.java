package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.UserStatsPanel;

import javax.swing.*;

public class UserStatsWindow extends AbstractWindow<UserStatsPanel> {

	public UserStatsWindow(String user) {
		super("Statistics for " + user, 300, 300, JFrame.DISPOSE_ON_CLOSE, new UserStatsPanel(user));
	}



}
