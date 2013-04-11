package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetrics;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class BenchmarkTest {

	@Test
	public void testGetLowestPredIndex() throws Exception {
		Transform<Integer, Project> numToProj = new Transform<Integer, Project>() {
			@Override
			public Project invoke(Integer i) {
				return new Project("owner" + i, "repo" + i);
			}
		};
		List<Project> projs = ListHelpers.map(numToProj, ListHelpers.countUpTo(4));
		Transform<Integer, Float> tofloat = new Transform<Integer, Float>() {
			@Override
			public Float invoke(Integer i) {
				return Float.valueOf(i);
			}
		};
		List<Float> predVals = ListHelpers.map(tofloat, ListHelpers.countUpTo(4));
		int lpi = Benchmark.getLowestPredictionIndex(projs, predVals);
		Assert.assertEquals(0, lpi);
	}

	@Test
	public void testGetHighestPredIndex() throws Exception {
		Transform<Integer, Project> numToProj = new Transform<Integer, Project>() {
			@Override
			public Project invoke(Integer i) {
				return new Project("owner" + i, "repo" + i);
			}
		};
		List<Project> projs = ListHelpers.map(numToProj, ListHelpers.countUpTo(4));
		Transform<Integer, Float> tofloat = new Transform<Integer, Float>() {
			@Override
			public Float invoke(Integer i) {
				return Float.valueOf(i);
			}
		};
		List<Float> predVals = ListHelpers.map(tofloat, ListHelpers.countUpTo(4));
		int hpi = Benchmark.getHighestPredIndex(projs, predVals);
		Assert.assertEquals(3, hpi);
	}

	@Test
	public void testCountCorrectPredictions() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = new TestData().invoke();
		List<ProjectMetrics> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();

		Benchmark.Quality q = Benchmark.countCorrectPredictions(predMethods, pml, rpl);
		Assert.assertEquals(2, q.bcCorrect);
		Assert.assertEquals(0, q.teCorrect);
	}

	private Benchmark.PredictionMethods getTestPredictionMethods() {
		return new Benchmark.PredictionMethods() {
				@Override
				public String getName() {
					return "Method1";
				}

				@Override
				public float testEffortPredictionMeasure(ProjectMetrics m) {
					return m.testLinesOfCode;
				}

				@Override
				public float bugCountPredictionMeasure(ProjectMetrics m) {
					return m.linesOfCode;
				}
			};
	}

	@Test
	public void testCalcPredictionValues() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = new TestData().invoke();
		List<ProjectMetrics> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();
		Benchmark.PredictionTypes type = Benchmark.PredictionTypes.BugCount;
		List<Float> vals = Benchmark.calcPredictionValues(predMethods, type, pml, rpl.get(0).toProjectList());
		Assert.assertEquals(1000.0f, vals.get(0));
		Assert.assertEquals(400.0f, vals.get(1));
	}

	@Test
	public void testSkipInvalidProjects() throws Exception {
	}

	@Test
	public void testMetricsForProject() throws Exception {
	}

	private class TestData {
		private List<ProjectMetrics> pml;
		private List<ResponseProjects> rpl;

		public List<ProjectMetrics> getPml() {
			return pml;
		}

		public List<ResponseProjects> getRpl() {
			return rpl;
		}

		public TestData invoke() {
			pml = new LinkedList<ProjectMetrics>();

			Project p1 = new Project("owner", "repo1");
			Project p2 = new Project("owner", "repo2");

			ProjectMetrics pm1 = new ProjectMetrics();
			pm1.linesOfCode = 400;
			pm1.testLinesOfCode = 200;
			pm1.project = p1;
			pml.add(pm1);

			ProjectMetrics pm2 = new ProjectMetrics();
			pm2.linesOfCode = 1000;
			pm2.testLinesOfCode = 400;
			pm2.project = p2;
			pml.add(pm2);

			rpl = new LinkedList<ResponseProjects>();

			ResponseProjects rp = new ResponseProjects();
			rp.user = "owner";
			rp.mostTested = p1.repoName;
			rp.leastTested = p2.repoName;
			rp.highestBugCount = p2.repoName;
			rp.lowestBugCount = p1.repoName;
			rpl.add(rp);
			return this;
		}
	}
}
