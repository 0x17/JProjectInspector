package org.andreschnabel.jprojectinspector.tests.offline.utilities.serialization;

import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvDataTest {

	private CsvData data;

	@Before
	public void setUp() throws Exception {
		List<String[]> rows = new LinkedList<String[]>();
		rows.add(new String[]{"A","B","C"});
		rows.add(new String[]{"1","2","3"});
		data = new CsvData(rows);
	}

	@Test
	public void testToString() {
		String str = data.toString();
		Assert.assertEquals("A,B,C\n1,2,3\n", str);
	}

}
