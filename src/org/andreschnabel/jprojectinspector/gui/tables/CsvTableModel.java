package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.pecker.serialization.CsvData;

import javax.swing.table.AbstractTableModel;

/**
 * Tabellenmodell für CSV-Daten.
 * Zeigt erste CSV-Zeile als Spaltenbezeichnungen an.
 * Restliche Zeilen als Tabellenzeilen.
 */
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
		return true;
	}

	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		super.setValueAt(val, rowIndex, columnIndex);
		data.setCellAt(rowIndex, columnIndex, (String)val);
	}
}
