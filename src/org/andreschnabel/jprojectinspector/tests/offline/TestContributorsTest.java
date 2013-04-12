package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaTestContributors;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TestContributorsTest {

	@Test
	public void testNumTestContribs() throws Exception {
		int numContribs = JavaTestContributors.numTestContribs(new File(TestCommon.MAIN_DIR));
		Assert.assertEquals(1, numContribs);
	}

	@Test
	public void testTraverseForContribs() {
	}

	@Test
	public void testContribNamesForFile() throws Exception {
		List<String> contribNames = GitHelpers.contribNamesForFile(new File(TestCommon.MAIN_DIR + File.separator + "README.md"));
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {"0x17"}, contribNames);
	}

}
