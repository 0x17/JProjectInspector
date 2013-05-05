package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.InputPanel;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;

/**
 * Eingabedialog
 * @see InputPanel
 */
public class InputWindow extends AbstractWindowWithParent<InputPanel> {

	private static final long serialVersionUID = 1L;

	public InputWindow(JFrame parentFrame) {
		super("Inputs", 1000, 600, JFrame.DISPOSE_ON_CLOSE, new InputPanel(), parentFrame);
		disposeOnClose();
	}

	public static void main(String[] args) {
		try {
			InputWindow iw = new InputWindow(null);
			iw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			iw.setVisible(true);
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
