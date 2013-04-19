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

	@Test
	public void testCountCorrectPredictions() throws Exception {
		Benchmark.PredictionMethods predMethods = getTestPredictionMethods();
		TestData testData = new TestData().invoke();
		List<ProjectWithResults> pml = testData.getPml();
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
		TestData testData = new TestData().invoke();
		List<ProjectWithResults> pml = testData.getPml();
		List<ResponseProjects> rpl = testData.getRpl();
		PredictionType type = PredictionType.BugCount;
		List<Double> vals = Benchmark.calcPredictionValues(predMethods, type, pml, rpl.get(0).toProjectList());
		Assert.assertEquals(1000.0, vals.get(0), TestCommon.DELTA);
		Assert.assertEquals(400.0, vals.get(1), TestCommon.DELTA);
	}

	@Test
	public void testSkipInvalidProjects() throws Exception {
	}

	@Test
	public void testMetricsForProject() throws Exception {
	}

	private class TestData {
		private List<ProjectWithResults> pml;
		private List<ResponseProjects> rpl;

		public List<ProjectWithResults> getPml() {
			return pml;
		}

		public List<ResponseProjects> getRpl() {
			return rpl;
		}

		public TestData invoke() {
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
			return this;
		}
	}
}
