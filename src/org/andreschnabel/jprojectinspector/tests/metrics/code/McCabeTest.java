package org.andreschnabel.jprojectinspector.tests.metrics.code;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.metrics.code.McCabe;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class McCabeTest {

	private McCabe mc;

	@Before
	public void setUp() throws Exception {
		this.mc = new McCabe();
	}

	@Test
	public void testDetermineMcCabeForSrcStr() throws Exception {
		assertEquals(4, mc.determineMcCabeForSrcStr("if for while case"));		
	}

	@Test
	public void testDetermineMcCabeForSrcFile() throws Exception {
		assertEquals(0, mc.determineMcCabeForSrcFile(new File(TestCommon.TEST_SRC_FILENAME)), 0.01f);
	}

	@Test
	public void testDetermineMcCabeForDir() throws Exception {
		assertEquals(0, mc.determineMcCabeForDir(new File(TestCommon.TEST_SRC_DIRECTORY)), 0.01f);
	}

}
