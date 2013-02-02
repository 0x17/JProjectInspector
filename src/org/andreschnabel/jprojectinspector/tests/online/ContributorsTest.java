package org.andreschnabel.jprojectinspector.tests.online;

import static org.junit.Assert.assertEquals;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Test;

public class ContributorsTest {

	@Test
	public void testCountNumContributors() throws Exception {
		assertEquals(93, Contributors.countNumContributors(new Project("mono", "MonoGame")));
		assertEquals(1, Contributors.countNumContributors(TestCommon.THIS_PROJECT));
	}

	@Test
	public void testCountNumTestContributors() {
	}

}
