package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.gui.panels.LauncherPanel;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LauncherWindow extends AbstractWindow<LauncherPanel> {

	private static final long serialVersionUID = 1L;

	public LauncherWindow() {
		super("Launcher", 300, 300, JFrame.EXIT_ON_CLOSE, new LauncherPanel());
	}

	public static void launch() throws Exception {
		GuiHelpers.setNativeLaf();
		final LauncherWindow lw = new LauncherWindow();

		if(Config.initializePathsFailed()) {
			final SettingsWindow sw = new SettingsWindow(null);
			sw.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					lw.setVisible(true);
				}
			});
			sw.setVisible(true);
		} else {
			lw.setVisible(true);
		}
	}

	public static void main(String[] args) throws Exception {
		launch();
	}

}
