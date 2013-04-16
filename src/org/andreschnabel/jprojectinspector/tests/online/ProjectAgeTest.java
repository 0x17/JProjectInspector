package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Assert;
import org.junit.Test;

public class ProjectAgeTest {

	@Test
	public void testGetProjectAge() throws Exception {
		long age = ProjectAge.getProjectAge(TestCommon.THIS_PROJECT);
		Assert.assertFalse(age == 0);
	}

}
