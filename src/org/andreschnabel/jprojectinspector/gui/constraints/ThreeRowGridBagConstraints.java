package org.andreschnabel.jprojectinspector.gui.constraints;

import java.awt.*;

public final class ThreeRowGridBagConstraints {

	private ThreeRowGridBagConstraints() {}

	public static GridBagConstraints topPaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		return gbc;
	}

	public static GridBagConstraints middlePaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = gbc.weighty = 1;
		return gbc;
	}

	public static GridBagConstraints bottomPaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 2;
		return gbc;
	}

}
