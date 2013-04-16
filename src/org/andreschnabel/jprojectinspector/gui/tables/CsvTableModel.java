package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.jprojectinspector.model.CsvData;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CsvTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final List<String[]> rows;

	public CsvTableModel(CsvData data) {
		this.rows = data.getRows();
	}

	@Override
	public int getRowCount() {
		return rows.size()-1;
	}

	@Override
	public int getColumnCount() {
		return rows.get(0).length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex+1)[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return rows.get(0)[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
