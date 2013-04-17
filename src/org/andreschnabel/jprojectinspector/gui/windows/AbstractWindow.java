package org.andreschnabel.jprojectinspector.gui.windows;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class AbstractWindow<T extends JPanel> extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected T panel;

	public AbstractWindow(String title, int width, int height, int closeOperation, T panel) {
		super(title);
		setLayout(new GridLayout(1,1));
		setDefaultCloseOperation(closeOperation);
		add(panel);
		setSize(width, height);
		setLocationRelativeTo(null);
		this.panel = panel;
	}
}
