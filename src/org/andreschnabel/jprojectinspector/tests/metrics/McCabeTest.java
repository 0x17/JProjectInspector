package org.andreschnabel.jprojectinspector.tests.metrics;

import org.andreschnabel.jprojectinspector.metrics.code.McCabe;
import org.junit.Before;
import org.junit.Test;

public class McCabeTest {

	private McCabe mc;

	@Before
	public void setUp() throws Exception {
		this.mc = new McCabe();
	}

	@Test
	public void testDetermineMcCabeForSrcStr() {
		//mc.determineMcCabeForSrcStr(srcStr);
	}

	@Test
	public void testDetermineMcCabeForSrcFile() {
	}

	@Test
	public void testDetermineMcCabeForDir() {
	}

}
