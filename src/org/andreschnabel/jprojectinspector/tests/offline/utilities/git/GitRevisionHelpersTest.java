package org.andreschnabel.jprojectinspector.tests.offline.utilities.git;

import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class GitRevisionHelpersTest {

	@Test
	public void testNumRevisions() throws Exception {
		int numRevisions = GitRevisionHelpers.numRevisions(new File("."));
		Assert.assertTrue(numRevisions > 0);
	}

	@Test
	public void testListAllRevisions() throws Exception {
		String[] revisions = GitRevisionHelpers.listAllRevisions(new File("."));
		Assert.assertFalse(revisions[0].startsWith("usage: git"));
		AssertHelpers.arrayNotEmpty(revisions);
		for(String revision : revisions) {
			Assert.assertEquals(40, revision.length()); // SHA1 is 40 chars long.
		}
	}

	@Test
	public void testListRevisionsInYear() throws Exception {
		String[] revisionsIn2012 = GitRevisionHelpers.listRevisionsInYear(new File("."), 2012);
		String[] revisionsIn2013 = GitRevisionHelpers.listRevisionsInYear(new File("."), 2013);
		AssertHelpers.arrayNotEmpty(revisionsIn2012);
		AssertHelpers.arrayNotEmpty(revisionsIn2013);
		Assert.assertFalse(revisionsIn2012[0].startsWith("usage: git"));
		Assert.assertFalse(revisionsIn2013[0].startsWith("usage: git"));
		Assert.assertTrue(revisionsIn2013.length > revisionsIn2012.length);
	}

	@Test
	public void testListRevisionsBetweenDates() throws Exception {
		String[] revisionsIn2012 = GitRevisionHelpers.listRevisionsBetweenDates(new File("."), "01.01.2012", "31.12.2012");
		AssertHelpers.arrayNotEmpty(revisionsIn2012);
		Assert.assertFalse(revisionsIn2012[0].startsWith("usage: git"));
		Assert.assertEquals(55, revisionsIn2012.length);
	}

	@Test
	public void testListRevisionsUntilDate() throws Exception {
		String[] revisionsIn2012 = GitRevisionHelpers.listRevisionsBeforeDate(new File("."), "31.12.2012");
		AssertHelpers.arrayNotEmpty(revisionsIn2012);
		Assert.assertFalse(revisionsIn2012[0].startsWith("usage: git"));
		Assert.assertEquals(55, revisionsIn2012.length);
	}

	@Test
	public void testLatestRevisionBeforeDate() throws Exception {
		String latestSha1 = GitRevisionHelpers.latestRevisionBeforeDate(new File("."), "2012-12-18");
		Assert.assertEquals("43878103962d51bcac7e5317d8805030edc73f0b", latestSha1);
	}

}
