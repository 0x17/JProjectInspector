package org.andreschnabel.jprojectinspector.metrics.code;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.utilities.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class AverageWMCTest {

	private AverageWMC mc;

	@Before
	public void setUp() throws Exception {
		this.mc = new AverageWMC();
	}

	@Test
	public void testDetermineMcCabeForSrcStr() throws Exception {
		//assertEquals(4, mc.determineMcCabeForSrcStr("if for while case"));		
	}

	@Test
	public void testDetermineMcCabeForSrcFile() throws Exception {
		//assertEquals(0, mc.determineMcCabeForSrcFile(new File(TestCommon.TEST_SRC_FILENAME)), 0.01f);
	}

	@Test
	public void testDetermineMcCabeForDir() throws Exception {
		assertEquals(0, mc.determineAverageWMC(new File(TestCommon.TEST_SRC_DIRECTORY)), 0.01f);
	}

}
