package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.junit.Assert;
import org.junit.Test;

public class ProjectDownloaderTest {
	@Test
	public void testLoadProject() throws Exception {
	}

	@Test
	public void testDeleteProject() throws Exception {
	}

	@Test
	public void testLoadProjects() throws Exception {
	}

	@Test
	public void testRevertProjectsToDate() throws Exception {
	}

	@Test
	public void testShaFromLatestCommitAtDate() throws Exception {
	}

	@Test
	public void testPreloadPath() throws Exception {
	}

	@Test
	public void testProjectFromFolderName() throws Exception {
	}

	@Test
	public void testDeleteEmtpyPreloads() throws Exception {
	}

	@Test
	public void testGetPreloadPaths() throws Exception {
	}

	@Test
	public void testGetPreloadProjs() throws Exception {
	}

	@Test
	public void testIsProjectOnline() throws Exception {
		Assert.assertTrue(ProjectDownloader.isProjectOnline(Project.fromString("0x17/JProjectInspector")));
		Assert.assertTrue(ProjectDownloader.isProjectOnline(Project.fromString("0x17/KCImageCollector")));
		Assert.assertFalse(ProjectDownloader.isProjectOnline(Project.fromString("0x17/SomethingCrazy")));
	}
}
