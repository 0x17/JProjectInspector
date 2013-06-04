package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific.simplecoverage;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.TestMethodReferenceCounter;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMethodReferenceCounterTest {

	private static final String TESTDATA_TEST = TestCommon.TEST_SRC_DIRECTORY + "/PointsTest.java";

	@Test
	public void testDetermineUniqueMethodsReferncedInTestStr() throws Exception {
		String src = FileHelpers.readEntireFile(new File(TESTDATA_TEST));
		List<String> testedMethodNames = new LinkedList<String>();
		TestMethodReferenceCounter.determineUniqueMethodsReferencedInTestStr(src, testedMethodNames);
		String[] expectedMethodNames = {"Position2D", "getLocation"};
		assertEquals(expectedMethodNames.length, testedMethodNames.size());
		for(int i = 0; i < expectedMethodNames.length; i++) {
			assertEquals(expectedMethodNames[i], testedMethodNames.get(i));
		}
	}

	@Test
	public void testDetermineUniqueMethodsReferencedInTests() throws Exception {
		List<String> testedMethodNames = new LinkedList<String>();
		TestMethodReferenceCounter.determineUniqueMethodsReferencedInTests(new File(TestCommon.TEST_SRC_DIRECTORY), testedMethodNames);
		String[] expectedMethodNames = {"Position2D", "getLocation"};
		assertEquals(expectedMethodNames.length, testedMethodNames.size());
		for(int i = 0; i < expectedMethodNames.length; i++) {
			assertEquals(expectedMethodNames[i], testedMethodNames.get(i));
		}
	}

}
