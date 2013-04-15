package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.model.CsvData;

import javax.swing.*;
import java.awt.*;

public class CsvTableWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public CsvTableWindow(CsvData data) {
		super(data.title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		JTable csvTbl = new JTable(new CsvTableModel(data));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(new JScrollPane(csvTbl), gbc);
		setSize(640, 480);
		setLocationRelativeTo(null);
	}

}
