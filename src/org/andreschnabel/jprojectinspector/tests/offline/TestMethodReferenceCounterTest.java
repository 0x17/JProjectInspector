package org.andreschnabel.jprojectinspector.tests.offline;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.TestMethodReferenceCounter;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Before;
import org.junit.Test;

public class TestMethodReferenceCounterTest {

	private static final String TESTDATA_TEST = TestCommon.TEST_SRC_DIRECTORY + "/PointsTest.java";
	private TestMethodReferenceCounter tmrc;

	@Before
	public void setUp() throws Exception {
		this.tmrc = new TestMethodReferenceCounter();
	}

	@Test
	public void testDetermineUniqueMethodsReferncedInTestStr() throws Exception {
		String src = FileHelpers.readEntireFile(new File(TESTDATA_TEST));
		List<String> testedMethodNames = new LinkedList<String>();
		tmrc.determineUniqueMethodsReferencedInTestStr(src, testedMethodNames);
		String[] expectedMethodNames = {"Position2D", "getLocation"};
		assertEquals(expectedMethodNames.length, testedMethodNames.size());
		for(int i = 0; i < expectedMethodNames.length; i++) {
			assertEquals(expectedMethodNames[i], testedMethodNames.get(i));
		}
	}

	@Test
	public void testDetermineUniqueMethodsReferencedInTests() throws Exception {
		List<String> testedMethodNames = new LinkedList<String>();
		tmrc.determineUniqueMethodsReferencedInTests(new File(TestCommon.TEST_SRC_DIRECTORY), testedMethodNames);
		String[] expectedMethodNames = {"Position2D", "getLocation"};
		assertEquals(expectedMethodNames.length, testedMethodNames.size());
		for(int i = 0; i < expectedMethodNames.length; i++) {
			assertEquals(expectedMethodNames[i], testedMethodNames.get(i));
		}
	}

}
