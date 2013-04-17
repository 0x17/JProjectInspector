package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Assert;
import org.junit.Test;

public class UnitTestDetectorOnlineTest {
	@Test
	public void testContainsTestAndLoad() throws Exception {
		Assert.assertTrue(UnitTestDetector.containsTestAndLoad(new Project("skeeto", "sample-java-project")));
		Assert.assertTrue(UnitTestDetector.containsTestAndLoad(new Project("0x17", "KCImageCollector")));
		Assert.assertFalse(UnitTestDetector.containsTestAndLoad(new Project("0x17", "DeathJam")));
	}
}
