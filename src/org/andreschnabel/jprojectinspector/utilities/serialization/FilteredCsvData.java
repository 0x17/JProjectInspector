package org.andreschnabel.jprojectinspector.utilities.serialization;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;

public class FilteredCsvData extends CsvData {
	private final CsvData actualData;

	public FilteredCsvData(CsvData actualData) {
		super(new CsvData(actualData));
		this.actualData = actualData;
	}

	public void filter(Predicate<String[]> rowPred) {
		rowList = Func.filter(rowPred, actualData.rowList);
	}
}