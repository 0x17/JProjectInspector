package org.andreschnabel.jprojectinspector.tests.online.metrics;

import static org.junit.Assert.assertFalse;

import org.andreschnabel.jprojectinspector.metrics.project.RecentCommits;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.junit.Test;

public class RecentCommitsTest {

	@Test
	public void testGetNumOfRecentCommits() throws Exception {
		int nrc = RecentCommits.getNumOfRecentCommits(TestCommon.THIS_PROJECT);
		assertFalse(nrc == 0);
	}

}
