package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.junit.Before;
import org.junit.Test;

public class TestLinesOfCodeTest {

	private TestLinesOfCode tloc;

	@Before
	public void setUp() throws Exception {
		this.tloc = new TestLinesOfCode();
	}

	@Test
	public void testCountTestLocOfDir() throws Exception {
		int locOfDir = tloc.countTestLocOfDir(new File(TestCommon.TEST_SRC_DIRECTORY));
		assertEquals(11, locOfDir);
	}

}
