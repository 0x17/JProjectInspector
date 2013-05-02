package org.andreschnabel.jprojectinspector.tests.offline.utilities.git;

import org.andreschnabel.jprojectinspector.utilities.git.GitContributorHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GitContributorHelpersTest {

	@Test
	public void testContribNamesForFile() throws Exception {
		List<String> contribs = GitContributorHelpers.contribNamesForFile(new File("README.md"));
		Assert.assertFalse(contribs.get(0).startsWith("usage: git"));
		AssertHelpers.listNotEmpty(contribs);
		Assert.assertTrue(contribs.get(0).contains("0x17"));
	}

	@Test
	public void testNumContribs() throws Exception {
		Assert.assertTrue(GitContributorHelpers.numContribs(new File(".")) > 0);
	}

	@Test
	public void testListAllContribs() throws Exception {
		List<String> contribs = GitContributorHelpers.listAllContribs(new File("dummydata"));
		Assert.assertFalse(contribs.get(0).startsWith("usage: git"));
		AssertHelpers.listNotEmpty(contribs);
		Assert.assertTrue(contribs.get(0).contains("0x17"));
		Assert.assertEquals(1, contribs.size());
	}

}
