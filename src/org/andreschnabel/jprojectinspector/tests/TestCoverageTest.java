package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.TestCoverage;
import org.junit.Before;
import org.junit.Test;

public class TestCoverageTest {

	private TestCoverage tc;

	@Before
	public void setUp() throws Exception {
		this.tc = new TestCoverage();
	}

	@Test
	public void testDetermineMethodCoverage() throws Exception {
		float coverage = tc.determineMethodCoverage(new File(TestCommon.TEST_SRC_DIRECTORY));
		assertEquals(0.4f, coverage, TestCommon.DELTA);
	}

}
