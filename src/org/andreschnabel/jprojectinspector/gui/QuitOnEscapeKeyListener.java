package org.andreschnabel.jprojectinspector.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class QuitOnEscapeKeyListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == 27) {
			System.exit(0);
		}
	}
}
