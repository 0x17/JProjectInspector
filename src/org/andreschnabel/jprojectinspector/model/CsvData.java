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
		for(String[] strings : rowList) {
			for(String string : strings) {
				sb.append(string + ",");
			}
			sb.append(";;");
		}
		return "CsvData"+title+"{" + sb + '}';
	}
}
