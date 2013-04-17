package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.table.AbstractTableModel;

public class CsvTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final CsvData data;

	public CsvTableModel(CsvData data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.rowCount();
	}

	@Override
	public int getColumnCount() {
		return data.columnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.getCellAt(rowIndex, columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return data.columnName(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
