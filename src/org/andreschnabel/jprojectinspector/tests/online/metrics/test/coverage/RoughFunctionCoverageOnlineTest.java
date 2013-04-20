package org.andreschnabel.jprojectinspector.tests.online.metrics.test.coverage;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class RoughFunctionCoverageOnlineTest {
	@Test
	public void testApproxFunctionCoverage() throws Exception {

	}

	@Test
	public void testMeasure() throws Exception {
		Project p = new Project("0x17", "KCImageCollector");
		try {
			File path = ProjectDownloader.loadProject(p);
			RoughFunctionCoverage rfc = new RoughFunctionCoverage();
			double result = rfc.measure(path);
			Assert.assertTrue(result > 0.0);
			Assert.assertTrue(result <= 1.0);
		} finally {
			ProjectDownloader.deleteProject(p);
		}
	}
}
