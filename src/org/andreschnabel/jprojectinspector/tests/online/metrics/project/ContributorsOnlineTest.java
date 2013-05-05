package org.andreschnabel.jprojectinspector.tests.online.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOnline;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContributorsOnlineTest {

	@Test
	public void testCountNumContributors() throws Exception {
		assertEquals(11, ContributorsOnline.countNumContributors(Project.fromString("jlnr/gosu")));
		assertEquals(1, ContributorsOnline.countNumContributors(TestCommon.THIS_PROJECT));
	}

}
