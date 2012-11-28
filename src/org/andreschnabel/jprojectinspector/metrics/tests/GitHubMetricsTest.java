
package org.andreschnabel.jprojectinspector.metrics.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.andreschnabel.jprojectinspector.metrics.GitHubMetrics;
import org.junit.Before;
import org.junit.Test;

public class GitHubMetricsTest {

	private GitHubMetrics ghm;

	@Before
	public void setUp() throws Exception {
		ghm = new GitHubMetrics("unclebob", "fitnesse");
	}

	@Test
	public void testGetNumberOfContributors() {
		try {
			assertEquals(79, ghm.getNumberOfContributors());
		} catch (Exception e) {
			assertTrue("Get num contribs exception!", false);
		}
	}

	@Test
	public void testGetRepositoryAge() {
		/*try {
			long age = ghm.getRepositoryAge();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
