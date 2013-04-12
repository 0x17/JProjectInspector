package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class UnitTestDetectorTest {

	@Test
	public void testIsTest() throws Exception {
		Assert.assertTrue(UnitTestDetector.isTest(new File("testdata/PointsTest.java")));
		Assert.assertFalse(UnitTestDetector.isTest(new File("testdata/Points.java")));
	}

	@Test
	public void testContainsTest() throws Exception {
		Assert.assertTrue(UnitTestDetector.containsTestAndLoad(new Project("skeeto", "sample-java-project")));
		Assert.assertTrue(UnitTestDetector.containsTestAndLoad(new Project("0x17", "ProjectInspector")));
	}
}
