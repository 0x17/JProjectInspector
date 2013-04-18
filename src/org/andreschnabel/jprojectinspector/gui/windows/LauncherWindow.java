package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.LauncherPanel;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;

public class LauncherWindow extends AbstractWindow<LauncherPanel> {

	public LauncherWindow() {
		super("Launcher", 300, 300, JFrame.EXIT_ON_CLOSE, new LauncherPanel());
	}

	public static void main(String[] args) throws Exception {
		GuiHelpers.setNativeLaf();
		new LauncherWindow().setVisible(true);
	}

}
