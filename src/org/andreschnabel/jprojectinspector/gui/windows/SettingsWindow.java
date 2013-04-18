package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.LaunchSettings;
import org.andreschnabel.jprojectinspector.gui.panels.SettingsPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindow extends AbstractWindow<SettingsPanel> {

	private static final long serialVersionUID = 1L;
	public LaunchSettings launcher;

	public SettingsWindow(final LaunchSettings launcher) {
		super("Settings", JFrame.DISPOSE_ON_CLOSE, new SettingsPanel());
		this.launcher = launcher;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				if(launcher != null) {
					launcher.closeSettings();
				}
			}
		});
	}
}

