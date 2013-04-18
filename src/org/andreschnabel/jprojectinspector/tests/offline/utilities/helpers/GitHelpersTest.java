package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GitHelpersTest {

	private final static String TEST_SHA1 = "52c8a477c6bce5bca8c1c1c000c8c4f4a33d6f8d";
	private final static String TEST_SHA2 = "01563b388c5c3ded90675c78d850ea758b55e112";

	@Test
	public void testContribNamesForFile() throws Exception {
		List<String> contribs = GitHelpers.contribNamesForFile(new File("README.md"));
		Assert.assertFalse(contribs.get(0).startsWith("usage: git"));
		AssertHelpers.listNotEmpty(contribs);
		Assert.assertTrue(contribs.get(0).contains("0x17"));
	}

	@Test
	public void testNumContribs() throws Exception {
		Assert.assertTrue(GitHelpers.numContribs(new File(".")) > 0);
	}

	@Test
	public void testListAllContribs() throws Exception {
		List<String> contribs = GitHelpers.listAllContribs(new File("."));
		Assert.assertFalse(contribs.get(0).startsWith("usage: git"));
		AssertHelpers.listNotEmpty(contribs);
		Assert.assertTrue(contribs.get(0).contains("0x17"));
	}

	@Test
	public void testNumCommits() throws Exception {
		int numCommits = GitHelpers.numCommits(new File("."));
		Assert.assertTrue(numCommits > 0);
	}

	@Test
	public void testListAllCommits() throws Exception {
		String[] commits = GitHelpers.listAllCommits(new File("."));
		Assert.assertFalse(commits[0].startsWith("usage: git"));
		AssertHelpers.arrayNotEmpty(commits);
		for(String commit : commits) {
			Assert.assertEquals(40, commit.length()); // SHA1 is 40 chars long.
		}
	}

	@Test
	public void testListCommitsInYear() throws Exception {
		String[] commitsIn2012 = GitHelpers.listCommitsInYear(new File("."), 2012);
		String[] commitsIn2013 = GitHelpers.listCommitsInYear(new File("."), 2013);
		AssertHelpers.arrayNotEmpty(commitsIn2012);
		AssertHelpers.arrayNotEmpty(commitsIn2013);
		Assert.assertFalse(commitsIn2012[0].startsWith("usage: git"));
		Assert.assertFalse(commitsIn2013[0].startsWith("usage: git"));
		Assert.assertTrue(commitsIn2013.length > commitsIn2012.length);
	}

	@Test
	public void testListCommitsBetweenDates() throws Exception {
		String[] commitsIn2012 = GitHelpers.listCommitsBetweenDates(new File("."), "01.01.2012", "31.12.2012");
		AssertHelpers.arrayNotEmpty(commitsIn2012);
		Assert.assertFalse(commitsIn2012[0].startsWith("usage: git"));
		Assert.assertEquals(55, commitsIn2012.length);
	}

	@Test
	public void testListCommitsUntilDate() throws Exception {
		String[] commitsIn2012 = GitHelpers.listCommitsUntilDate(new File("."), "31.12.2012");
		AssertHelpers.arrayNotEmpty(commitsIn2012);
		Assert.assertFalse(commitsIn2012[0].startsWith("usage: git"));
		Assert.assertEquals(55, commitsIn2012.length);
	}

	@Test
	public void testParseChurnStatsFromShortStat() throws Exception {
		String shortStat = "2 files changed, 10 insertions(+)";
		GitHelpers.ChurnStats stats = GitHelpers.parseChurnStatsFromShortStat(shortStat);
		assertEqualsTestChurn(stats);
	}

	@Test
	public void testGetChurnStatsForCommit() throws Exception {
		GitHelpers.ChurnStats stats = GitHelpers.getChurnStatsForCommit(new File("."), TEST_SHA1);
		assertEqualsTestChurn(stats);
	}

	private void assertEqualsTestChurn(GitHelpers.ChurnStats stats) {
		Assert.assertEquals(2, stats.filesChanged);
		Assert.assertEquals(10, stats.numInsertions);
		Assert.assertEquals(0, stats.numDeletions);
	}

	@Test
	public void testGetChurnStatsBetweenCommits() throws Exception {
		GitHelpers.ChurnStats stats = GitHelpers.getChurnStatsBetweenCommits(new File("."), TEST_SHA1, TEST_SHA2);
		Assert.assertEquals(17, stats.filesChanged);
		Assert.assertEquals(588, stats.numInsertions);
		Assert.assertEquals(39, stats.numDeletions);
	}

	@Test
	public void testListCommitComments() throws Exception {
		List<String> commitComments = GitHelpers.listCommitComments(new File("."), new File("README.md"));
		AssertHelpers.listNotEmpty(commitComments);
		String last = commitComments.get(commitComments.size() - 1);
		Assert.assertEquals("Added README.md and TODO.md", last);
		Assert.assertEquals(3, commitComments.size());
	}
	
	@Test
	public void testLatestCommitUntilDate() throws Exception {
		String latestSha1 = GitHelpers.latestCommitUntilDate(new File("."), "2012-12-18");
		Assert.assertEquals("43878103962d51bcac7e5317d8805030edc73f0b", latestSha1);
	}
}
