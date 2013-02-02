package org.andreschnabel.jprojectinspector.tests.online;

import static org.junit.Assert.assertTrue;

import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Test;

public class UnitTestDetectorTest {

	@Test
	public void testContainsTest() throws Exception {
		assertTrue(UnitTestDetector.containsTestAndLoad(new Project("skeeto", "sample-java-project")));
		assertTrue(UnitTestDetector.containsTestAndLoad(new Project("0x17", "ProjectInspector")));
	}
}
