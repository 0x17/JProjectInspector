package org.andreschnabel.jprojectinspector.tests.offline;

import java.io.File;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.Pmd;
import org.junit.Test;

public class PmdTest {
	@Test
	public void testMeasure() throws Exception {
		double violationCount = new Pmd().measure(new File("src/org/andreschnabel/jprojectinspector/scrapers/"));
		Assert.assertEquals(6, (int)violationCount);
	}
}
