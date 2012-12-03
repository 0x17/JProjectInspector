package org.andreschnabel.jprojectinspector.testprevalence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JUnitTestDetectorTest {

	@Test
	public void testContainsTest() throws Exception {
		JUnitTestDetector td = new JUnitTestDetector();
		assertTrue(td.containsTest(new Project("skeeto","sample-java-project")));
		assertFalse(td.containsTest(new Project("0x17", "ProjectInspector")));
	}

}
