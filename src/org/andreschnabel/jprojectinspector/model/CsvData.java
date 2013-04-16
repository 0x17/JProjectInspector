package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvData {

	private List<String[]> rowList;
	public final String title;

	public CsvData(List<String[]> rowList) {
		this("untitled", rowList);
	}

	public CsvData(String title, List<String[]> rowList) {
		this.title = title;
		this.rowList = rowList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < rowList.size(); i++) {
			String[] row = rowList.get(i);
			for(int j = 0; j < row.length; j++) {
				String cell = row[j];
				sb.append(cell);
				if(j+1<row.length)
					sb.append(',');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public String[] getHeaders() {
		return rowList.get(0);
	}

	public int columnWithHeader(String header) {
		return ListHelpers.fromArray(getHeaders()).indexOf(header);
	}

	public int rowCount() {
		return rowList.size()-1;
	}

	public int columnCount() {
		return getHeaders().length;
	}

	public String getCellAt(int row, int col) {
		return rowList.get(row+1)[col];
	}

	public String getCellAt(int row, String header) {
		return rowList.get(row+1)[columnWithHeader(header)];
	}

	public void addColumn(String header) {
		Transform<String[], String[]> addColumnToRow = new Transform<String[], String[]>() {
			@Override
			public String[] invoke(String[] row) {
				return Arrays.copyOf(row, row.length+1);
			}
		};
		rowList = ListHelpers.map(addColumnToRow, rowList);
		rowList.get(0)[columnCount()-1] = header;
	}

	public static <T> CsvData fromList(String[] headers, Transform<T, String[]> elemToRow, List<T> lst) {
		List<String[]> rows = new ArrayList<String[]>(lst.size()+1);
		rows.add(headers);
		for(T elem : lst) {
			rows.add(elemToRow.invoke(elem));
		}
		return new CsvData(rows);
	}

	public void save(File file) throws Exception {
		FileHelpers.writeStrToFile(toString(), file);
	}

	public List<String[]> getRows() {
		return rowList;
	}

	public String[] getRow(int row) {
		return rowList.get(row);
	}
}
