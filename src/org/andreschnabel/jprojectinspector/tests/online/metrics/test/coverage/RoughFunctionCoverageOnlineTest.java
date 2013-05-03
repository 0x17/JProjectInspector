package org.andreschnabel.jprojectinspector.tests.online.metrics.test.coverage;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoughFunctionCoverageOnlineTest {
	@Test
	public void testApproxFunctionCoverage() throws Exception {
		Project p = Project.fromString("0x17/KCImageCollector");
		File path = ProjectDownloader.loadProject(p);
		Map<String, Double> cov = RoughFunctionCoverage.approxFunctionCoverage(path);
		double javaCov = cov.get("java");
		// Actual EclEmma coverage of project is ~11.8% allow 2% tolerance.
		Assert.assertEquals(0.118, javaCov, 0.02);
		ProjectDownloader.deleteProject(p);
	}

	@Test
	public void testMeasureForLanguagesWithIndexers() throws Exception {
		List<Project> projects = new LinkedList<Project>();
		projects.add(Project.fromString("0x17/KCImageCollector")); // Java, JUnit
		projects.add(Project.fromString("jimm/midilib")); // Ruby, default 'test/unit'
		projects.add(Project.fromString("StefanKjartansson/django-gotuskra")); // Python, default 'unittest'
		projects.add(Project.fromString("mantoni/hub.js")); // JavaScript, default

		testMeasureWithProjects(projects);
	}

	@Test
	public void testMeasureForLanguagesWithoutIndexers() throws Exception {
		List<Project> projects = new LinkedList<Project>();
		projects.add(Project.fromString("phtrivier/cppunit-money-example-cmake")); // C++, CppUnit

		testMeasureWithProjects(projects);
	}

	private static void testMeasureWithProjects(List<Project> projects) throws Exception {
		for(Project p : projects) {
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
}
