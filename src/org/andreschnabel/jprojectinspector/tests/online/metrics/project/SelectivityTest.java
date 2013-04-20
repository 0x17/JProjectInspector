package org.andreschnabel.jprojectinspector.tests.online.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.project.Selectivity;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectivityTest {

	@Test
	public void testGetSelectivity() throws Exception {
		assertEquals(0, Selectivity.getSelectivity(TestCommon.THIS_PROJECT));
	}

}
