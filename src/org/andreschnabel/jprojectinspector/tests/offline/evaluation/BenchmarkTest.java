package org.andreschnabel.jprojectinspector.tests.offline.evaluation;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
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
		Transform<Integer, Float> tofloat = new Transform<Integer, Float>() {
			@Override
			public Float invoke(Integer i) {
				return Float.valueOf(i);
			}
		};
		List<Float> predVals = Func.map(tofloat, Func.countUpTo(4));
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
		Transform<Integer, Float> tofloat = new Transform<Integer, Float>() {
			@Override
			public Float invoke(Integer i) {
				return Float.valueOf(i);
			}
		};
		List<Float> predVals = Func.map(tofloat, Func.countUpTo(4));
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
				public float testEffortPredictionMeasure(ProjectWithResults m) {
					return m.get("testloc");
				}

				@Override
				public float bugCountPredictionMeasure(ProjectWithResults m) {
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

			Project p1 = new Project("owner", "repo1");
			Project p2 = new Project("owner", "repo2");

			ProjectWithResults pm1 = new ProjectWithResults(null, null, null);
			//pm1.linesOfCode = 400;
			//pm1.testLinesOfCode = 200;
			//pm1.project = p1;
			pml.add(pm1);

			ProjectWithResults pm2 = new ProjectWithResults(null, null, null);
			//pm2.linesOfCode = 1000;
			//pm2.testLinesOfCode = 400;
			//pm2.project = p2;
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
