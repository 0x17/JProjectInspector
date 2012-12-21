package org.andreschnabel.jprojectinspector.metrics.test.prevalence;

import static org.junit.Assert.assertTrue;

import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class UnitTestDetectorTest {
	
	private UnitTestDetector td;

	@Before
	public void setup() {
		td = new UnitTestDetector();
	}

	@Test
	public void testContainsTest() throws Exception {		
		assertTrue(td.containsTestAndLoad(new Project("skeeto","sample-java-project")));
		assertTrue(td.containsTestAndLoad(new Project("0x17", "ProjectInspector")));
	}
}
