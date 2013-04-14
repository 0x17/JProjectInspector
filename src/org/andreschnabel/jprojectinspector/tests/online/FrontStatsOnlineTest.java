package org.andreschnabel.jprojectinspector.tests.online;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Test;

public class FrontStatsOnlineTest {
	@Test
	public void testStatsForProject() throws Exception {
		FrontStats stats = FrontStats.statsForProject(new Project("0x17", "JProjectInspector"));
		Assert.assertEquals(1, stats.nbranches);
		Assert.assertTrue(162 < stats.ncommits);
		Assert.assertEquals(0, stats.nissues);
		Assert.assertEquals(0, stats.nforks);
		Assert.assertEquals(0, stats.nstars);
		Assert.assertEquals(0, stats.npullreqs);
	}
}
