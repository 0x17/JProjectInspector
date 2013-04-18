package org.andreschnabel.jprojectinspector.gui.windows;

import javax.swing.*;

public abstract class AbstractWindowWithParent<T extends JPanel> extends AbstractWindow<T> {
	private final JFrame parentFrame;

	public AbstractWindowWithParent(String title, int closeOperation, T panel, JFrame parentFrame) {
		super(title, closeOperation, panel);
		this.parentFrame = parentFrame;
	}

	public AbstractWindowWithParent(String title, int width, int height, int closeOperation, T panel, JFrame parentFrame) {
		super(title, width, height, closeOperation, panel);
		this.parentFrame = parentFrame;
	}

	@Override
	public void dispose() {
		super.dispose();
		if(parentFrame != null) {
			parentFrame.setVisible(true);
		}
	}
}
