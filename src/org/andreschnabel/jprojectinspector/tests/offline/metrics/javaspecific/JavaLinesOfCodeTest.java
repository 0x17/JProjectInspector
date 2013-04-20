package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaLinesOfCode;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class JavaLinesOfCodeTest {
	@Test
	public void testCountLocOfSrcStr() throws Exception {
		assertEquals(0, JavaLinesOfCode.countLocOfSrcStr("//blabla"));
		assertEquals(1, JavaLinesOfCode.countLocOfSrcStr("//blabla\nclass Test {}"));
		String testSrcStr = FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME));
		assertEquals(43, JavaLinesOfCode.countLocOfSrcStr(testSrcStr)); // 43 according to cloc

		testSrcStr = FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_TEST_FILENAME));
		assertEquals(11, JavaLinesOfCode.countLocOfSrcStr(testSrcStr));
	}

	@Test
	public void testCountLocOfSrcFile() throws Exception {
		assertEquals(43, JavaLinesOfCode.countLocOfSrcFile(new File(TestCommon.TEST_SRC_FILENAME)));
	}

	@Test
	public void testCountLocOfDir() throws Exception {
		assertEquals(54, JavaLinesOfCode.countLocOfDir(new File(TestCommon.TEST_SRC_DIRECTORY)));
	}
}
