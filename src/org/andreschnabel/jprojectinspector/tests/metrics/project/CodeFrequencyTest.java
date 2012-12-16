package org.andreschnabel.jprojectinspector.tests.metrics.project;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.metrics.project.CodeFrequency;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class CodeFrequencyTest {
	
	private CodeFrequency cf;

	@Before
	public void setUp() throws Exception {
		this.cf = new CodeFrequency();
	}

	@Test
	public void testCountCodeFrequencyForProj() throws Exception {
		int cfVal = cf.countCodeFrequencyForProj(TestCommon.THIS_PROJECT);
		assertFalse(0 == cfVal);
	}

}
