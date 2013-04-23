package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Tautology;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.FilteredCsvData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;

public class CsvTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public CsvTablePanel(final CsvData data) {
		setLayout(new GridBagLayout());

		FilteredCsvData fdata = new FilteredCsvData(data);
		CsvTableModel csvTableModel = new CsvTableModel(fdata);
		final JTable csvTbl = new JTable(csvTableModel);

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

		JLabel filterExprLbl = new JLabel("Filter RegEx:");
		topPane.add(filterExprLbl);

		JTextField filterExprField = new JTextField(30);
		filterExprField.getDocument().addDocumentListener(new FilterIfRxIsValid(filterExprField, data, fdata, csvTbl));
		topPane.add(filterExprField);

		JButton addRowBtn = new JButton("Add row");
		addRowBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data.addRow(csvTbl.getSelectedRow());
				csvTbl.updateUI();
			}
		});
		topPane.add(addRowBtn);

		JButton rmRowBtn = new JButton("Remove row");
		rmRowBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data.removeRow(csvTbl.getSelectedRow());
				csvTbl.updateUI();
			}
		});
		topPane.add(rmRowBtn);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topPane);
	}

	private static class FilterIfRxIsValid implements DocumentListener {
		private final JTextField filterExprField;
		private final CsvData data;
		private final FilteredCsvData fdata;
		private final JTable csvTbl;
		public IPredicate<String[]> pred;

		public FilterIfRxIsValid(JTextField filterExprField, CsvData csvData, FilteredCsvData fdata, JTable csvTbl) {
			this.filterExprField = filterExprField;
			this.data = csvData;
			this.fdata = fdata;
			this.csvTbl = csvTbl;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			filterIfRxValid();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			filterIfRxValid();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			filterIfRxValid();
		}

		private void filterIfRxValid() {
			try {
				String text = filterExprField.getText();
				if(StringHelpers.countOccurencesOfWord(text, "=") == 1) {
					String[] parts = text.split("=");
					final String colName = parts[0];
					if(data.hasColumnWithHeader(colName)) {
						final Pattern pattern = Pattern.compile(parts[1]);
						pred = new IPredicate<String[]>() {
							@Override
							public boolean invoke(String[] sa) {
								int colIndex = data.columnWithHeader(colName);
								return pattern.matcher(sa[colIndex]).matches();
							}
						};
						fdata.filter(pred);
						csvTbl.updateUI();
					}
				} else {
					fdata.filter(new Tautology<String[]>());
					csvTbl.updateUI();
				}
			} catch(Exception e) {
			}
		}
	}
}
