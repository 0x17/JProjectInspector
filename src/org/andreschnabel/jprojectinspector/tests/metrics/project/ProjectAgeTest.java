package org.andreschnabel.jprojectinspector.tests.metrics.project;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class ProjectAgeTest {

	private ProjectAge pa;

	@Before
	public void setUp() throws Exception {
		this.pa = new ProjectAge();
	}

	@Test
	public void testGetProjectAge() throws Exception {
		long age = pa.getProjectAge(TestCommon.THIS_PROJECT);
		assertFalse(age == 0);
	}

}
