package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.CsvHelpers;
import org.junit.Test;

import java.io.File;

public class CsvHelpersTest {

	@Test
	public void testCountColumns() {
		Assert.assertEquals(1, CsvHelpers.countColumns("a"));
		Assert.assertEquals(2, CsvHelpers.countColumns("something,\"some text\""));
		Assert.assertEquals(3, CsvHelpers.countColumns("a,b,c"));		
	}

	@Test
	public void testParseCsvFile() throws Exception {
		CsvData data = CsvHelpers.parseCsv(new File("data/responses500.csv"));
		Assert.assertTrue(data.rowCount() > 0);
		Assert.assertTrue(data.columnCount() > 0);
	}

	@Test
	public void testParseCsvStringSingleLine() throws Exception {
		String csvLine = "Name,\"Age\",date of birth";
		String[] cols = new String[] {"Name", "\"Age\"", "date of birth"};
		
		CsvData data = CsvHelpers.parseCsv(csvLine);
		Assert.assertEquals(1, data.rowCount());
		
		String[] firstRow = data.getHeaders();
		AssertHelpers.arrayEqualsArrayOrderSensitive(cols, firstRow);
	}
	
	@Test
	public void testParseCsvStringMultiLines() throws Exception {
		String csvLines = "Name,Age,Comment\nPeter,23,Testcomment\nHans,44,Finish\n";
		
		String[] row1 = new String[] {"Name", "Age", "Comment"};
		String[] row2 = new String[] {"Peter", "23", "Testcomment"};
		String[] row3 = new String[] {"Hans", "44", "Finish"};

		CsvData data = CsvHelpers.parseCsv(csvLines);

		Assert.assertEquals(2, data.rowCount());
		
		AssertHelpers.arrayEqualsArrayOrderSensitive(row1, data.getHeaders());
		AssertHelpers.arrayEqualsArrayOrderSensitive(row2, data.getRow(0));
		AssertHelpers.arrayEqualsArrayOrderSensitive(row3, data.getRow(1));
	}

}
