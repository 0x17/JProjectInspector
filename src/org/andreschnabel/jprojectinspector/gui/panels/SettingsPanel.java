package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.gui.windows.SettingsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends PanelWithParent {

	private static final long serialVersionUID = 1L;
	
	private final JTextField[] fields;
	private static final String[] settingNames = new String[] { "Temp clone folder", "GitHub base url", "Git executable path", "Cloc executable path" };

	public SettingsPanel() {
		int nrows = settingNames.length;
		setLayout(new GridLayout(nrows+1, 2));
		JLabel[] lbls = new JLabel[nrows];
		fields = new JTextField[nrows];
		for(int i=0; i<nrows; i++) {
			lbls[i] = new JLabel(settingNames[i]);
			add(lbls[i]);
			fields[i] = new JTextField();
			add(fields[i]);
		}
		fields[0].setText(Config.DEST_BASE);
		fields[1].setText(Config.BASE_URL);
		fields[2].setText(Config.GIT_PATH);
		fields[3].setText(Config.CLOC_PATH);
		setSize(400, 300);

		JButton hideBtn = new JButton("Cancel");
		hideBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		add(hideBtn);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettings();
				close();
			}
		});
		add(saveBtn);
	}

	private void close() {
		((SettingsWindow)parentFrame).parentPanel.closeSettings();
		parentFrame.dispose();
	}

	private void saveSettings() {
		Config.DEST_BASE = fields[0].getText();
		Config.BASE_URL = fields[1].getText();
		Config.GIT_PATH = fields[2].getText();
		Config.CLOC_PATH = fields[3].getText();
	}
}
