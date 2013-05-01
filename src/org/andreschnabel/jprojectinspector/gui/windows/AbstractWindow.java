package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.PanelWithParent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Abstrakte Klasse für Fenster.
 *
 * Für weniger Boilerplate-Code bei der Erstellung von Fenstern.
 *
 * @param <T> Typ des zentralen Panels vom Fenster.
 */
public abstract class AbstractWindow<T extends JPanel> extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected T panel;

	public AbstractWindow(String title, int closeOperation, T panel) {
		super("JProjectInspector :: " + title);
		setLayout(new GridLayout(1,1));
		setDefaultCloseOperation(closeOperation);

		add(panel);
		this.panel = panel;

		pack();
		setLocationRelativeTo(null);

		if(panel instanceof PanelWithParent) {
			((PanelWithParent) panel).setParentFrame(this);
		}
	}

	public AbstractWindow(String title, int width, int height, int closeOperation, T panel) {
		this(title, closeOperation, panel);
		setSize(width, height);
		setLocationRelativeTo(null);
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
