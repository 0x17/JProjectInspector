package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.andreschnabel.jprojectinspector.testprevalence.model.Project;
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
		assertTrue(td.containsTest(new Project("skeeto","sample-java-project")));
		assertFalse(td.containsTest(new Project("0x17", "ProjectInspector")));
	}
}
