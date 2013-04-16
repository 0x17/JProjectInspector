package org.andreschnabel.jprojectinspector.tests.online;

import static org.junit.Assert.assertFalse;

import org.andreschnabel.jprojectinspector.metrics.project.CodeFrequency;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

public class CodeFrequencyTest {

	@Test
	public void testCountCodeFrequencyForProj() throws Exception {
		int cfVal = CodeFrequency.countCodeFrequencyForProj(TestCommon.THIS_PROJECT);
		assertFalse(0 == cfVal);
	}

}
