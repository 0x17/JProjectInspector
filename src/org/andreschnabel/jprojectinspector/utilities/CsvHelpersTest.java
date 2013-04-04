package org.andreschnabel.jprojectinspector.utilities;

import junit.framework.Assert;

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
	public void testParseCsvStringSingleLine() {
	}
	
	@Test
	public void testParseCsvStringMultiLines() {
	}

}
