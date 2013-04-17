package org.andreschnabel.jprojectinspector.gui.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractWindow<T extends JPanel> extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected T panel;

	public AbstractWindow(String title, int width, int height, int closeOperation, T panel) {
		this(title, width, height, closeOperation, panel, false);
	}

	public AbstractWindow(String title, int width, int height, int closeOperation, T panel, boolean doPack) {
		super("JProjectInspector :: " + title);
		setLayout(new GridLayout(1,1));
		setDefaultCloseOperation(closeOperation);
		add(panel);
		setSize(width, height);
		if(doPack) {
			pack();
		}
		setLocationRelativeTo(null);
		this.panel = panel;
	}

	protected void disposeOnClose() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				super.windowClosing(e);
			}
		});
	}
}
