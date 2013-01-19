package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class ContributorsTest {

	private Contributors contribs;

	@Before
	public void setUp() throws Exception {
		this.contribs = new Contributors();
	}

	@Test
	public void testCountNumContributors() throws Exception {
		assertEquals(85, contribs.countNumContributors(new Project("mono", "MonoGame")));
		assertEquals(1, contribs.countNumContributors(TestCommon.THIS_PROJECT));
	}

	@Test
	public void testCountNumTestContributors() {
	}

}
