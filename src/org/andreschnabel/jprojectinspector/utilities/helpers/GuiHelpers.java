package org.andreschnabel.jprojectinspector.utilities.helpers;

import javax.swing.*;
import java.awt.*;

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

	public static GridBagConstraints fillHorizontalConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		return gbc;
	}

	public static GridBagConstraints fillBothConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		return gbc;
	}
}
