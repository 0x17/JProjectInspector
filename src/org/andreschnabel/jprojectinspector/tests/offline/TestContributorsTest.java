package org.andreschnabel.jprojectinspector.tests.offline;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.test.TestContributors;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Test;

public class TestContributorsTest {

	@Test
	public void testNumTestContribs() {
	}

	@Test
	public void testTraverseForContribs() {
	}

	@Test
	public void testContribNamesForFile() throws Exception {
		List<String> contribNames = TestContributors.contribNamesForFile(new File("C:\\Users\\Andre\\Dropbox\\Code\\JProjectInspector\\README.md"));
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {"0x17"}, contribNames);
	}

}
