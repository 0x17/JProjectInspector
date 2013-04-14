package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOnline;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContributorsOnlineTest {

	@Test
	public void testCountNumContributors() throws Exception {
		assertEquals(93, ContributorsOnline.countNumContributors(new Project("mono", "MonoGame")));
		assertEquals(1, ContributorsOnline.countNumContributors(TestCommon.THIS_PROJECT));
	}

	@Test
	public void testCountNumTestContributors() {
	}

}
