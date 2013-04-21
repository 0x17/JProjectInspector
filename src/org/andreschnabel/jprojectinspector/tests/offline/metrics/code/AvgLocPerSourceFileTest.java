package org.andreschnabel.jprojectinspector.tests.offline.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.code.AvgLocPerSourceFile;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class AvgLocPerSourceFileTest {
	@Test
	public void testMeasure() throws Exception {
		double avgLocPerSource = new AvgLocPerSourceFile().measure(new File("."));
		Assert.assertTrue(avgLocPerSource > 0.0);
	}
}
