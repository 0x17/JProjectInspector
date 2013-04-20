package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific.simplecoverage;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SimpleJavaTestCoverageTest {

	@Test
	public void testDetermineMethodCoverage() throws Exception {
		Double coverage = SimpleJavaTestCoverage.determineMethodCoverage(new File(TestCommon.TEST_SRC_DIRECTORY));
		assertEquals(0.4f, coverage, TestCommon.DELTA);
	}

}
