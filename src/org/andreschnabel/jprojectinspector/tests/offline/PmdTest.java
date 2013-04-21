package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.Pmd;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import java.io.File;

public class PmdTest {
	@Test
	public void testMeasure() throws Exception {
		double violationCount = new Pmd().measure(new File("."));
		Assert.assertTrue(violationCount > 0.0);
		Assert.assertEquals(216.0, violationCount, TestCommon.DELTA);
	}
}
