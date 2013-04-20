package org.andreschnabel.jprojectinspector.tests.offline.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.code.LocPerSourceFile;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class LocPerSourceFileTest {
	@Test
	public void testMeasure() throws Exception {
		double lpsf = new LocPerSourceFile().measure(new File("testdata"));
		Assert.assertEquals(27.0, lpsf, TestCommon.DELTA);
	}
}
