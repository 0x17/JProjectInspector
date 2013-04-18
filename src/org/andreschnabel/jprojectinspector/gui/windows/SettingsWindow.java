package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.InputPanel;
import org.andreschnabel.jprojectinspector.gui.panels.SettingsPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindow extends AbstractWindow<SettingsPanel> {

	private static final long serialVersionUID = 1L;
	public InputPanel parentPanel;

	public SettingsWindow(final InputPanel parentPanel) {
		super("Settings", JFrame.DISPOSE_ON_CLOSE, new SettingsPanel());
		this.parentPanel = parentPanel;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parentPanel.closeSettings();
				super.windowClosing(e);
			}
		});
	}

	public static void main(String[] args) {
		new InputWindow().setVisible(true);
	}

}

