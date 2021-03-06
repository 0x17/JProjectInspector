package org.andreschnabel.jprojectinspector.tests.offline.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestLinesOfCodeTest {
	@Test
	public void testCountTestLocHeuristic() throws Exception {
		int tloc = TestLinesOfCode.countTestLocHeuristic(new File("dummydata"));
		Assert.assertEquals(11, tloc);
	}
}
