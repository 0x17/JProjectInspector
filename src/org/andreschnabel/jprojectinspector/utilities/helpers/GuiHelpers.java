package org.andreschnabel.jprojectinspector.utilities.helpers;

import javax.swing.*;

public class GuiHelpers {

	public static void setNimbusLaf() throws Exception {
		if(Helpers.runningOnUnix()) {
			return;
		}

		for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	}

}
