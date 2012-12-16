package org.andreschnabel.jprojectinspector.tests.metrics.project;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.metrics.project.CommitActivity;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class CommitActivityTest {

	private CommitActivity ca;

	@Before
	public void setUp() throws Exception {
		this.ca = new CommitActivity();
	}

	@Test
	public void testGetNumOfRecentCommits() throws Exception {
		int nrc = ca.getNumOfRecentCommits(TestCommon.THIS_PROJECT);
		assertFalse(nrc == 0);
	}

}
