package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.InputPanel;

import javax.swing.*;

public class InputWindow extends AbstractWindow<InputPanel> {

	private static final long serialVersionUID = 1L;

	public InputWindow() {
		super("Inputs", 750, 540, JFrame.EXIT_ON_CLOSE, new InputPanel());
		disposeOnClose();
	}

	public static void main(String[] args) {
		new InputWindow().setVisible(true);
	}

	@Override
	public void dispose() {
		super.dispose();
		panel.dispose();
	}
}
