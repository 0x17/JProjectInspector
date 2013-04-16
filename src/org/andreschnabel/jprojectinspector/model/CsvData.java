package org.andreschnabel.jprojectinspector.model;

import java.util.List;

public class CsvData {

	public final List<String[]> rowList;
	public String title;

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
}
