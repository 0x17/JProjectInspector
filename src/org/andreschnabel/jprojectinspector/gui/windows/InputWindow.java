package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.InputPanel;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;

public class InputWindow extends AbstractWindow<InputPanel> {

	private static final long serialVersionUID = 1L;

	public InputWindow() {
		super("Inputs", 800, 600, JFrame.EXIT_ON_CLOSE, new InputPanel());
		disposeOnClose();
	}

	public static void main(String[] args) {
		try {
			new InputWindow().setVisible(true);
		} catch(Exception e) {
			GuiHelpers.showError(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		panel.dispose();
	}
}
