package org.andreschnabel.jprojectinspector.tests.online.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IssuesTest {
	@Test
	public void testGetNumberOfIssues() throws Exception {
		assertEquals(0, Issues.getNumberOfIssues(TestCommon.THIS_PROJECT));
		assertEquals(38, Issues.getNumberOfIssues(Project.fromString("jlnr/gosu")));
	}

}
