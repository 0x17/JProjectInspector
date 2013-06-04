package org.andreschnabel.jprojectinspector.tests.offline.utilities.git;

import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;
import org.andreschnabel.pecker.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GitHelpersTest {
	@Test
	public void testListCommitComments() throws Exception {
		List<String> commitComments = GitHelpers.listCommitMessages(new File("."), new File("README.md"));
		AssertHelpers.listNotEmpty(commitComments);
		String last = commitComments.get(commitComments.size() - 1);
		Assert.assertEquals("Added README.md and TODO.md", last);
		Assert.assertEquals(3, commitComments.size());
	}
}
