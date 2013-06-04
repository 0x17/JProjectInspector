package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ProjectDownloaderTest {

	@Test
	public void testIsProjectOnline() throws Exception {
		Assert.assertTrue(ProjectDownloader.isProjectOnline(Project.fromString("0x17/JProjectInspector")));
		Assert.assertTrue(ProjectDownloader.isProjectOnline(Project.fromString("0x17/KCImageCollector")));
		Assert.assertFalse(ProjectDownloader.isProjectOnline(Project.fromString("0x17/SomethingCrazy")));
	}

	@Test
	public void testLoadAndDeleteProject() throws Exception {
		Project p = Project.fromString("0x17/UnfollowDetectorDroid");

		File path = ProjectDownloader.loadProject(p);
		Assert.assertTrue(path.exists());
		Assert.assertTrue(FileHelpers.filesInTree(path).size() > 0);

		File newPath = ProjectDownloader.loadProject(p);
		Assert.assertEquals(newPath, path);
		Assert.assertTrue(path.exists());
		Assert.assertTrue(FileHelpers.filesInTree(path).size() > 0);

		ProjectDownloader.deleteProject(p);
		Assert.assertFalse(path.exists());
	}

}
