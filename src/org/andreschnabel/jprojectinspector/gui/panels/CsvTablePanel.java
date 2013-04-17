package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CsvTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public CsvTablePanel(final CsvData data) {
		setLayout(new GridBagLayout());

		JTable csvTbl = new JTable(new CsvTableModel(data));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(new JScrollPane(csvTbl), gbc);

		JPanel topPane = new JPanel(new FlowLayout());

		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					GuiHelpers.saveCsvDialog(new File("."), data);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		topPane.add(exportBtn);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topPane);
	}
}
