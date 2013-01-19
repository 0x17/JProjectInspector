package org.andreschnabel.jprojectinspector.tests;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class LinesOfCodeTest {
	@Test
	public void testCountLocOfSrcStr() throws Exception {
		assertEquals(0, LinesOfCode.countLocOfSrcStr("//blabla"));
		assertEquals(1, LinesOfCode.countLocOfSrcStr("//blabla\nclass Test {}"));
		String testSrcStr = FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME));
		assertEquals(43, LinesOfCode.countLocOfSrcStr(testSrcStr)); // 43 according to cloc

		testSrcStr = FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_TEST_FILENAME));
		assertEquals(11, LinesOfCode.countLocOfSrcStr(testSrcStr));
	}

	@Test
	public void testCountLocOfSrcFile() throws Exception {
		assertEquals(43, LinesOfCode.countLocOfSrcFile(new File(TestCommon.TEST_SRC_FILENAME)));
	}

	@Test
	public void testCountLocOfDir() throws Exception {
		assertEquals(54, LinesOfCode.countLocOfDir(new File(TestCommon.TEST_SRC_DIRECTORY)));
	}

	@Test
	public void testCountLocForProj() {
	}
}
