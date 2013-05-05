package org.andreschnabel.jprojectinspector;

import org.andreschnabel.jprojectinspector.gui.windows.LauncherWindow;
import org.andreschnabel.jprojectinspector.gui.windows.LogWindow;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

/**
 * Starter für Oberfläche.
 */
public class GuiRunner {

	/**
	 * Einstiegspunkt für die grafische Oberfläche.
	 * @param args Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		try {
			new LogWindow().setVisible(true);
			LauncherWindow.launch();
		} catch(Exception e) {
			Helpers.log(e.toString());
			e.printStackTrace();
		}
	}

}
