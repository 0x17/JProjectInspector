package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Parse GoogleDocs CSV results.
 * List of rows.
 * '([col11 col12 ... col1n] ... [colm1 colm2 ... colmn])
 */
public class CsvHelpers {
	
	public static int countColumns(String line) {
		int numColums = 1;
		boolean escaped = false;
		for(int i=0; i<line.length(); i++) {
			char c = line.charAt(i);
			switch(c) {
			case ',':
				if(!escaped) numColums++;
				break;
			case '"':
				escaped = !escaped;
				break;
			}
		}
		return numColums;
	}
	
	public static List<String[]> parseCsv(File f) throws Exception {
		return parseCsv(FileHelpers.readEntireFile(f));
	}
	
	public static List<String[]> parseCsv(String content) throws Exception {
		List<String[]> rows = new LinkedList<String[]>();	
		String firstLine = content.substring(0, content.indexOf("\n"));
		int numColumns = countColumns(firstLine);
		
		String[] curRow = new String[numColumns];
		int curColumn = 0;
		
		StringBuffer buf = new StringBuffer();
		
		boolean escaped = false;
		for(int i=0; i<content.length(); i++) {
			char c = content.charAt(i);
			switch(c) {
			case '"':
				escaped = !escaped;
				break;
			case ',':
				if(!escaped) {
					if(curColumn == numColumns) {
						rows.add(curRow);
						curRow = new String[numColumns];
						curColumn = 0;
					}
					curRow[curColumn++] = buf.toString();
					buf = new StringBuffer();
				}
				break;
			default:
				buf.append(c);
				break;
			}
		}
		
		return rows;
	}
	
}
