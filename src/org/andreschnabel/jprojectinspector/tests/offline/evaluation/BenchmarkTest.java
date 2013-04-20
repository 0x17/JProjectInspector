package org.andreschnabel.jprojectinspector.tests.offline.evaluation;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.PredictionType;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class BenchmarkTest {

	@Test
	public void testRunBenchmark() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = getTestData();
		List<ProjectWithResults> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();

		Benchmark.Quality q = Benchmark.runBenchmark(predMethods, pml, rpl);
		Assert.assertEquals(2, q.bcCorrect);
		Assert.assertEquals(0, q.teCorrect);

		Assert.assertEquals("repo1", q.bcPredictions.get(0)[0]);
		Assert.assertEquals("repo2", q.bcPredictions.get(0)[1]);

		Assert.assertEquals("repo1", q.tePredictions.get(0)[0]);
		Assert.assertEquals("repo2", q.tePredictions.get(0)[1]);
	}

	@Test
	public void testGetLowestPredIndex() throws Exception {
		Transform<Integer, Project> numToProj = new Transform<Integer, Project>() {
			@Override
			public Project invoke(Integer i) {
				return new Project("owner" + i, "repo" + i);
			}
		};
		List<Project> projs = Func.map(numToProj, Func.countUpTo(4));
		Transform<Integer, Double> toDouble = new Transform<Integer, Double>() {
			@Override
			public Double invoke(Integer i) {
				return Double.valueOf(i);
			}
		};
		List<Double> predVals = Func.map(toDouble, Func.countUpTo(4));
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
		List<Project> projs = Func.map(numToProj, Func.countUpTo(4));
		Transform<Integer, Double> toDouble = new Transform<Integer, Double>() {
			@Override
			public Double invoke(Integer i) {
				return Double.valueOf(i);
			}
		};
		List<Double> predVals = Func.map(toDouble, Func.countUpTo(4));
		int hpi = Benchmark.getHighestPredIndex(projs, predVals);
		Assert.assertEquals(3, hpi);
	}

	private Benchmark.PredictionMethods getTestPredictionMethods() {
		return new Benchmark.PredictionMethods() {
				@Override
				public String getName() {
					return "Method1";
				}

				@Override
				public double testEffortPredictionMeasure(ProjectWithResults m) {
					return m.get("testloc");
				}

				@Override
				public double bugCountPredictionMeasure(ProjectWithResults m) {
					return m.get("loc");
				}
			};
	}

	@Test
	public void testCalcPredictionValues() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = getTestData();
		List<ProjectWithResults> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();
		PredictionType type = PredictionType.BugCount;
		List<Double> vals = Benchmark.calcPredictionValues(predMethods, type, pml, rpl.get(0).toProjectList());
		Assert.assertEquals(1000.0, vals.get(0), TestCommon.DELTA);
		Assert.assertEquals(400.0, vals.get(1), TestCommon.DELTA);
	}

	@Test
	public void isInvalidProject() throws Exception {
		List<ProjectWithResults> projectWithResultsList = getTestData().getPml();
		List<Project> projectList = Func.map(new Transform<ProjectWithResults, Project>() {
			@Override
			public Project invoke(ProjectWithResults p) {
				return p.project;
			}
		},projectWithResultsList);
		Assert.assertFalse(Benchmark.isInvalidProject(projectWithResultsList, projectList));
		projectWithResultsList.remove(0);
		Assert.assertTrue(Benchmark.isInvalidProject(projectWithResultsList, projectList));
	}

	@Test
	public void testMetricsForProject() throws Exception {
		List<ProjectWithResults> projectWithResultsList = getTestData().getPml();
		ProjectWithResults projectWithResults = Benchmark.metricsForProject(new Project("owner", "repo1"), projectWithResultsList);
		Assert.assertEquals(projectWithResultsList.get(0), projectWithResults);
		Assert.assertNull(Benchmark.metricsForProject(new Project("not", "inthere"), projectWithResultsList));
	}

	private static TestData getTestData() {
		List<ProjectWithResults> pml;
		List<ResponseProjects > rpl;

		pml = new LinkedList<ProjectWithResults>();

		String[] resultHeaders = new String[] {"loc", "testloc"};

		Project p1 = new Project("owner", "repo1");

		Double[] results = new Double[] {400.0, 200.0};
		ProjectWithResults pm1 = new ProjectWithResults(p1, resultHeaders, results);
		pml.add(pm1);

		results = new Double[] {1000.0, 400.0};
		Project p2 = new Project("owner", "repo2");
		ProjectWithResults pm2 = new ProjectWithResults(p2, resultHeaders, results);
		pml.add(pm2);

		rpl = new LinkedList<ResponseProjects>();

		ResponseProjects rp = new ResponseProjects();
		rp.user = "owner";
		rp.mostTested = p1.repoName;
		rp.leastTested = p2.repoName;
		rp.highestBugCount = p2.repoName;
		rp.lowestBugCount = p1.repoName;
		rp.weight = 1.0;
		rpl.add(rp);

		return new TestData(pml, rpl);
	}

	private static class TestData {
		private List<ProjectWithResults> pml;
		private List<ResponseProjects> rpl;

		private TestData(List<ProjectWithResults> pml, List<ResponseProjects> rpl) {
			this.pml = pml;
			this.rpl = rpl;
		}

		public List<ProjectWithResults> getPml() {
			return pml;
		}

		public List<ResponseProjects> getRpl() {
			return rpl;
		}
	}
}
