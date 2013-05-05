package org.andreschnabel.jprojectinspector.tests.online.metrics.project;

import static org.junit.Assert.assertFalse;

import org.andreschnabel.jprojectinspector.metrics.churn.CodeFrequency;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

public class CodeFrequencyTest {

	@Test
	public void testCountCodeFrequencyForProj() throws Exception {
		double cfVal = CodeFrequency.countCodeFrequencyForProj(TestCommon.THIS_PROJECT);
		assertFalse(Double.isNaN(cfVal));
	}

}
