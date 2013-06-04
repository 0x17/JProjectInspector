package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.pecker.helpers.IObserver;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel implements IObserver<String> {
	private static final long serialVersionUID = 1L;
	
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
		if(msg.contains("\r")) {
			removeLastLine();
		} else {
			logArea.append(msg);
		}
	}

	private void removeLastLine() {
		String txt = logArea.getText();
		String[] lines = txt.split("\n");
		String lastLine = lines[lines.length-1];
		logArea.setText(txt.replace(lastLine, ""));
	}
}
