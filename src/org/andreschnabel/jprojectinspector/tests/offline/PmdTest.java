package org.andreschnabel.jprojectinspector.tests.offline;

import java.io.File;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.Pmd;
import org.junit.Test;

public class PmdTest {
	@Test
	public void testMeasure() throws Exception {
		double violationCount = new Pmd().measure(new File("."));
		Assert.assertTrue(violationCount > 0.0);
		Assert.assertTrue(violationCount > 200.0);
		Assert.assertTrue(violationCount < 300.0);
	}
}
