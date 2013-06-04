package org.andreschnabel.jprojectinspector.tests.offline.evaluation;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.ITransform;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BenchmarkTest {

	@Test
	public void testRunBenchmark() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = getTestData();
		Map<Project, ProjectWithResults> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();

		Benchmark.Quality q = Benchmark.runBenchmark(predMethods, pml, rpl, false);
		Assert.assertEquals(1, q.bcCorrect);
		Assert.assertEquals(0, q.teCorrect);

		Assert.assertEquals("repo1", q.bcPredictions.get(0)[0]);
		Assert.assertEquals("repo2", q.bcPredictions.get(0)[1]);

		Assert.assertEquals("repo1", q.tePredictions.get(0)[0]);
		Assert.assertEquals("repo2", q.tePredictions.get(0)[1]);
	}

	@Test
	public void testGetLowestPredIndex() throws Exception {
		ITransform<Integer, Project> numToProj = new ITransform<Integer, Project>() {
			@Override
			public Project invoke(Integer i) {
				return new Project("owner" + i, "repo" + i);
			}
		};
		List<Project> projs = Func.map(numToProj, Func.countUpTo(4));
		ITransform<Integer, Double> toDouble = new ITransform<Integer, Double>() {
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
		ITransform<Integer, Project> numToProj = new ITransform<Integer, Project>() {
			@Override
			public Project invoke(Integer i) {
				return new Project("owner" + i, "repo" + i);
			}
		};
		List<Project> projs = Func.map(numToProj, Func.countUpTo(4));
		ITransform<Integer, Double> toDouble = new ITransform<Integer, Double>() {
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
	public void containsInvalidProject() throws Exception {
		Map<Project, ProjectWithResults> projectWithResultsList = getTestData().getPml();
		List<Project> projectList = Func.map(new ITransform<ProjectWithResults, Project>() {
			@Override
			public Project invoke(ProjectWithResults p) {
				return p.project;
			}
		},projectWithResultsList.values());
		Assert.assertFalse(Benchmark.containsInvalidProject(projectWithResultsList.values(), projectList));
		projectWithResultsList.remove(projectWithResultsList.keySet().iterator().next());
		Assert.assertTrue(Benchmark.containsInvalidProject(projectWithResultsList.values(), projectList));
	}

	@Test
	public void testMetricsForProject() throws Exception {
		Map<Project, ProjectWithResults> projectWithResultsList = getTestData().getPml();
		ProjectWithResults projectWithResults = Benchmark.metricsForProject(new Project("owner", "repo1"), projectWithResultsList.values());
		Assert.assertEquals(projectWithResultsList.get(projectWithResultsList.keySet().iterator().next()), projectWithResults);
		Assert.assertNull(Benchmark.metricsForProject(new Project("not", "inthere"), projectWithResultsList.values()));
	}

	private static TestData getTestData() {
		Map<Project, ProjectWithResults> pml;
		List<ResponseProjects > rpl;

		pml = new HashMap<Project, ProjectWithResults>();

		String[] resultHeaders = new String[] {"loc", "testloc"};

		Project p1 = new Project("owner", "repo1");

		Double[] results = new Double[] {400.0, 200.0};
		ProjectWithResults pm1 = new ProjectWithResults(p1, resultHeaders, results);
		pml.put(p1, pm1);

		results = new Double[] {1000.0, 400.0};
		Project p2 = new Project("owner", "repo2");
		ProjectWithResults pm2 = new ProjectWithResults(p2, resultHeaders, results);
		pml.put(p2, pm2);

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
		private Map<Project, ProjectWithResults> pml;
		private List<ResponseProjects> rpl;

		private TestData(Map<Project, ProjectWithResults> pml, List<ResponseProjects> rpl) {
			this.pml = pml;
			this.rpl = rpl;
		}

		public Map<Project, ProjectWithResults> getPml() {
			return pml;
		}

		public List<ResponseProjects> getRpl() {
			return rpl;
		}
	}

	@Test
	public void testPermutate() throws Exception {
		//Assert.fail();
	}

	@Test
	public void testInsertMetricsForVarPlaceholders() throws Exception {
		String str = Benchmark.insertMetricsForVarPlaceholders("a*b/c", new String[]{"a", "b", "c"}, new String[]{"Metric1", "Metric2", "Metric3", "Metric4"}, new int[]{0, 3, 2});
		Assert.assertEquals("Metric1*Metric4/Metric3", str);
	}
}
