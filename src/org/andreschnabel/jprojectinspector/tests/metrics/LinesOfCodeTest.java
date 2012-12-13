package org.andreschnabel.jprojectinspector.tests.metrics;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.junit.Before;
import org.junit.Test;

public class LinesOfCodeTest {

	private LinesOfCode loc;

	@Before
	public void setUp() throws Exception {
		this.loc = new LinesOfCode();
	}
	
	@Test
	public void testCountLocOfSrcStr() throws Exception {
		assertEquals(0, loc.countLocOfSrcStr("//blabla"));
		assertEquals(1, loc.countLocOfSrcStr("//blabla\nclass Test {}"));
		String testSrcStr = Helpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME));
		assertEquals(43, loc.countLocOfSrcStr(testSrcStr)); // 43 according to cloc
	}
	
	@Test
	public void testCountLocOfSrcFile() {
	}
	
	@Test
	public void testCountLocOfDir() {
	}

	@Test
	public void testCountLocForProj() {
	}
}
