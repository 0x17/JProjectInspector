package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ProjectAgeTest {

	@Test
	public void testGetProjectAge() throws Exception {
		long age = ProjectAge.getProjectAge(TestCommon.THIS_PROJECT);
		assertFalse(age == 0);
	}

}
