package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.metrics.project.TestCommitMessages;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestCommitCommentsTest {
	@Test
	public void testCountNumTestCommitComments() throws Exception {
		int numTestCommitComments = TestCommitMessages.countNumTestCommitMessages(new File("."));
		Assert.assertTrue(numTestCommitComments > 0);
		Assert.assertTrue(numTestCommitComments < GitRevisionHelpers.numRevisions(new File(".")));
	}
}
