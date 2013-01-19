package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.assertEquals;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class IssuesTest {

	private Issues issues;

	@Before
	public void setUp() throws Exception {
		this.issues = new Issues();
	}

	@Test
	public void testGetNumberOfIssues() throws Exception {
		assertEquals(0, issues.getNumberOfIssues(TestCommon.THIS_PROJECT));
		assertEquals(187, issues.getNumberOfIssues(Project.fromString("mono/MonoGame")));
	}

}
