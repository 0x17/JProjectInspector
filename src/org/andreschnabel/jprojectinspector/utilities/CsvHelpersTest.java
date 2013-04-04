package org.andreschnabel.jprojectinspector.utilities;

import java.util.List;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.CsvHelpers;
import org.junit.Test;

public class CsvHelpersTest {

	@Test
	public void testCountColumns() {
		Assert.assertEquals(1, CsvHelpers.countColumns("a"));
		Assert.assertEquals(2, CsvHelpers.countColumns("something,\"some text\""));
		Assert.assertEquals(3, CsvHelpers.countColumns("a,b,c"));		
	}

	@Test
	public void testParseCsvFile() {
		Assert.fail();
	}

	@Test
	public void testParseCsvStringSingleLine() throws Exception {
		String csvLine = "Name,\"Age\",date of birth";
		String[] cols = new String[] {"Name", "\"Age\"", "date of birth"};
		
		List<String[]> rows = CsvHelpers.parseCsv(csvLine);
		Assert.assertEquals(1, rows.size());
		
		String[] firstRow = rows.get(0);
		AssertHelpers.arrayEqualsArrayOrderSensitive(cols, firstRow);
	}
	
	@Test
	public void testParseCsvStringMultiLines() throws Exception {
		String csvLines = "Name,Age,Comment\nPeter,23,Testcomment\nHans,44,Finish\n";
		
		String[] row1 = new String[] {"Name", "Age", "Comment"};
		String[] row2 = new String[] {"Peter", "23", "Testcomment"};
		String[] row3 = new String[] {"Hans", "44", "Finish"};
		
		List<String[]> rows = CsvHelpers.parseCsv(csvLines);
		
		Assert.assertEquals(3, rows.size());
		
		AssertHelpers.arrayEqualsArrayOrderSensitive(row1, rows.get(0));		
		AssertHelpers.arrayEqualsArrayOrderSensitive(row2, rows.get(1));
		AssertHelpers.arrayEqualsArrayOrderSensitive(row3, rows.get(2));
	}

}
