package org.andreschnabel.jprojectinspector.tests.online.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class UnitTestDetectorOnlineTest {

	private File pintosPath;
	private Project pintos;
	private Project kcImgCollector;
	private File kcImgCollectorPath;

	private void loadPintos() throws Exception {
		pintos = Project.fromString("willscott/pintos");
		pintosPath = ProjectDownloader.loadProject(pintos);
	}

	private void loadKcImageCollector() throws Exception {
		kcImgCollector = Project.fromString("0x17/KCImageCollector");
		kcImgCollectorPath = ProjectDownloader.loadProject(kcImgCollector);
	}

	private void deletePintos() throws Exception {
		ProjectDownloader.deleteProject(pintos);
	}

	private void deleteKcImageCollector() throws Exception {
		ProjectDownloader.deleteProject(kcImgCollector);
	}

	@Test
	public void testPintosContainsTest() throws Exception {
		loadPintos();
		Assert.assertTrue(UnitTestDetector.containsTest(pintosPath));
		deletePintos();
	}

	@Test
	public void testKCImageCollectorContainsTest() throws Exception {
		loadKcImageCollector();
		Assert.assertTrue(UnitTestDetector.containsTest(kcImgCollectorPath));
		deleteKcImageCollector();
	}

	@Test
	public void testIsTest() throws Exception {
		loadKcImageCollector();
		File testfile = new File(kcImgCollectorPath.getPath()+"/src/org/ox17/kcimagecollector/tests/KCImageCollectorTest.java");
		Assert.assertTrue(UnitTestDetector.isTest(testfile));
		File nontestfile = new File(kcImgCollectorPath.getPath()+"/src/org/ox17/kcimagecollector/ImageScaler.java");
		Assert.assertFalse(UnitTestDetector.isTest(nontestfile));
		deleteKcImageCollector();
	}

	@Test
	public void testGetTestFilesKcImgCollector() throws Exception {
		loadKcImageCollector();
		List<File> testFiles = UnitTestDetector.getTestFiles(kcImgCollectorPath);
		File[] expectedTestFiles = new File[] {
				new File(kcImgCollectorPath.getPath()+"/src/org/ox17/kcimagecollector/tests/KCImageCollectorTest.java")
		};
		AssertHelpers.arrayEqualsLstOrderInsensitive(expectedTestFiles, testFiles);
		deleteKcImageCollector();
	}

	@Test
	public void testGetTestFilesPintos() throws Exception {
		loadPintos();
		List<File> testFiles = UnitTestDetector.getTestFiles(pintosPath);
		Assert.assertTrue(testFiles.size() > 10);
		deletePintos();
	}
}
