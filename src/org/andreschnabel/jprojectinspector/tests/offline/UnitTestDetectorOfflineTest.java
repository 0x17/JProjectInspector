package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class UnitTestDetectorOfflineTest {

	@Test
	public void testIsTest() throws Exception {
		Assert.assertTrue(UnitTestDetector.isTest(new File("testdata/PointsTest.java")));
		Assert.assertFalse(UnitTestDetector.isTest(new File("testdata/Points.java")));
	}

	@Test
	public void testGetTestFiles() throws Exception {
		List<File> testfiles = UnitTestDetector.getTestFiles(new File("testdata"));
		AssertHelpers.arrayEqualsLstOrderInsensitive(new File[]{new File("testdata/PointsTest.java")}, testfiles);
	}

	@Test
	public void testContainsTest() throws Exception {
		Assert.assertTrue(UnitTestDetector.containsTest(new File("testdata")));
	}

}
