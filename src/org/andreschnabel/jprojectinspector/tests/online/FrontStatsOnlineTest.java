package org.andreschnabel.jprojectinspector.tests.online;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Test;

public class FrontStatsOnlineTest {
	@Test
	public void testStatsForThisProject() throws Exception {
		FrontStats stats = FrontStats.statsForProject(new Project("0x17", "JProjectInspector"));
		Assert.assertEquals(1, stats.nbranches);
		Assert.assertTrue(162 < stats.ncommits);
		Assert.assertEquals(0, stats.nissues);
		Assert.assertEquals(0, stats.nforks);
		Assert.assertEquals(0, stats.nstars);
		Assert.assertEquals(0, stats.npullreqs);
	}

	@Test
	public void testStatsForJuliansGosu() throws Exception {
		FrontStats stats = FrontStats.statsForProject(new Project("jlnr", "gosu"));
		Assert.assertTrue(2 <= stats.nbranches);
		Assert.assertTrue(1000 <= stats.ncommits);
		Assert.assertTrue(37 <= stats.nissues);
		Assert.assertTrue(47 <= stats.nforks);
		Assert.assertTrue(396 <= stats.nstars);
		Assert.assertTrue(2 <= stats.npullreqs);
	}

	@Test
	public void testOfflineProject() throws Exception {
		FrontStats stats = FrontStats.statsForProject(new Project("0x17", "nonexistant"));
		Assert.assertEquals(new FrontStats(), stats);
	}
}
