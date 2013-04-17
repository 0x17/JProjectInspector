package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.LauncherPanel;

import javax.swing.*;

public class LauncherWindow extends AbstractWindow<LauncherPanel> {

	public LauncherWindow() {
		super("Launcher", JFrame.EXIT_ON_CLOSE, new LauncherPanel());
	}

	public static void main(String[] args) {
		new LauncherWindow().setVisible(true);
	}

}
