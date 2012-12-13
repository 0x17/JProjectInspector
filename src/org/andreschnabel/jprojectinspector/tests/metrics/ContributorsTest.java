package org.andreschnabel.jprojectinspector.tests.metrics;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.andreschnabel.jprojectinspector.ProjectDownloader;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class ContributorsTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCountNumContributors() throws Exception {
		ProjectDownloader pd = new ProjectDownloader();
		Project p = new Project("0x17", "JProjectInspector");
		File root = pd.loadProject(p);
		Contributors cc = new Contributors();
		int numContribs = cc.countNumContributors(root);
		System.out.println("Number of contributors: " + numContribs);
		assertEquals(2, numContribs);
		pd.deleteProject(p);
	}

	@Test
	public void testCountNumTestContributors() {
	}

}
