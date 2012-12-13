package org.andreschnabel.jprojectinspector.tests.metrics;

import static org.junit.Assert.*;

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
		assertEquals(0, issues.getNumberOfIssues(new Project("0x17", "JProjectInspector")));
	}

}
