package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JTextField[] fields;

	private static final String[] settingNames = new String[] { "Base folder", "Base url", "Git executable path", "Cloc executable path" };

	public SettingsWindow() {
		super("Settings");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton hideBtn = new JButton("Hide");
		hideBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(hideBtn);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettings();
			}
		});
		add(saveBtn);
	}

	private void saveSettings() {
		Config.DEST_BASE = fields[0].getText();
		Config.BASE_URL = fields[1].getText();
	}
}

