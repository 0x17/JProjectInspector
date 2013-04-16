package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CsvTableWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public CsvTableWindow(final CsvData data) {
		super(data.title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());

		JTable csvTbl = new JTable(new CsvTableModel(data));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(new JScrollPane(csvTbl), gbc);

		JPanel bottomPane = new JPanel(new FlowLayout());

		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					GuiHelpers.saveStringWithFileDialog(data.toString(), new File("."), "csv");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		bottomPane.add(exportBtn);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(bottomPane);

		setSize(640, 480);
		setLocationRelativeTo(null);
	}

}
