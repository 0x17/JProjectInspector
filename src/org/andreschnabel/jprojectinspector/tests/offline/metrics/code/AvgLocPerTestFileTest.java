package org.andreschnabel.jprojectinspector.tests.offline.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.code.AvgLocPerTestFile;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class AvgLocPerTestFileTest {
	@Test
	public void testMeasure() throws Exception {
		double avgLocPerTestFile = new AvgLocPerTestFile().measure(new File("dummydata"));
		Assert.assertEquals(11, (int)avgLocPerTestFile);
	}
}
