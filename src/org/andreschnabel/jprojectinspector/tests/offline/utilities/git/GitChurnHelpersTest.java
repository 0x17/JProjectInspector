package org.andreschnabel.jprojectinspector.tests.offline.utilities.git;

import org.andreschnabel.jprojectinspector.utilities.git.GitChurnHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class GitChurnHelpersTest {

	private final static String TEST_SHA1 = "52c8a477c6bce5bca8c1c1c000c8c4f4a33d6f8d";
	private final static String TEST_SHA2 = "01563b388c5c3ded90675c78d850ea758b55e112";

	@Test
	public void testParseChurnStatsFromShortStat() throws Exception {
		String shortStat = "2 files changed, 10 insertions(+)";
		GitChurnHelpers.ChurnStats stats = GitChurnHelpers.parseChurnStatsFromShortStat(shortStat);
		assertEqualsTestChurn(stats);
	}

	@Test
	public void testGetChurnStatsForCommit() throws Exception {
		GitChurnHelpers.ChurnStats stats = GitChurnHelpers.getChurnStatsForRevision(new File("."), TEST_SHA1);
		assertEqualsTestChurn(stats);
	}

	private void assertEqualsTestChurn(GitChurnHelpers.ChurnStats stats) {
		Assert.assertEquals(2, stats.filesChanged);
		Assert.assertEquals(10, stats.numInsertions);
		Assert.assertEquals(0, stats.numDeletions);
	}

	@Test
	public void testGetChurnStatsBetweenCommits() throws Exception {
		GitChurnHelpers.ChurnStats stats = GitChurnHelpers.getChurnStatsBetweenRevisions(new File("."), TEST_SHA1, TEST_SHA2);
		Assert.assertEquals(17, stats.filesChanged);
		Assert.assertEquals(588, stats.numInsertions);
		Assert.assertEquals(39, stats.numDeletions);
	}
}
