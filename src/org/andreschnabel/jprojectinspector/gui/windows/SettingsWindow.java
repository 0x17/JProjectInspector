package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.SettingsPanel;

import javax.swing.*;

public class SettingsWindow extends AbstractWindow<SettingsPanel> {

	private static final long serialVersionUID = 1L;

	public SettingsWindow() {
		super("Settings", 400, 300, JFrame.HIDE_ON_CLOSE, new SettingsPanel());
	}

	public static void main(String[] args) {
		new InputWindow().setVisible(true);
	}

}

