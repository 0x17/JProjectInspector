package org.andreschnabel.jprojectinspector.tests.online;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.metrics.project.RecentCommits;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Before;
import org.junit.Test;

public class CommitActivityTest {

	private RecentCommits ca;

	@Before
	public void setUp() throws Exception {
		this.ca = new RecentCommits();
	}

	@Test
	public void testGetNumOfRecentCommits() throws Exception {
		int nrc = ca.getNumOfRecentCommits(TestCommon.THIS_PROJECT);
		assertFalse(nrc == 0);
	}

}
