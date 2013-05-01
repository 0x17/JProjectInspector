package org.andreschnabel.jprojectinspector.utilities.serialization;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;

/**
 * Gefilterte CSV-Daten, in denen nur Zeilen, die Prädikat erfüllen, enthalten sind.
 */
public class FilteredCsvData extends CsvData {
	private final CsvData actualData;

	/**
	 * Konstruktor
	 * @param actualData ursprüngliche CSV-Daten.
	 */
	public FilteredCsvData(CsvData actualData) {
		super(new CsvData(actualData));
		this.actualData = actualData;
	}

	/**
	 * Enthalte nur noch Zeilen, die Prädikat erfüllen.
	 * @param rowPred Predikat für eine Zeile.
	 */
	public void filter(IPredicate<String[]> rowPred) {
		rowList = Func.filter(rowPred, actualData.rowList);
	}
}
