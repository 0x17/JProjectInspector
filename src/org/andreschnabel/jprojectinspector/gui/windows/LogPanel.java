package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.utilities.helpers.IObserver;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel implements IObserver<String> {

	private final JTextArea logArea;

	public LogPanel() {
		super(new GridLayout(1,1));
		logArea = new JTextArea(20, 20);
		Font fnt = new Font("Monospaced", Font.PLAIN, 12);
		logArea.setFont(fnt);
		add(new JScrollPane(logArea));
	}


	@Override
	public void update(String msg) {
		logArea.append(msg);
	}
}
