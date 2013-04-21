package org.andreschnabel.jprojectinspector.tests.offline.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ProjectAgeTest {

	@Test
	public void testGetProjectAge() throws Exception {
		long age = ProjectAge.getProjectAge(new File("."));
		Assert.assertTrue(age > 0);
	}

}
