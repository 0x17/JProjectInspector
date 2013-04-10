package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.junit.Test;

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
	}

	@Test
	public void testCalcPredictionValues() throws Exception {
	}

	@Test
	public void testSkipInvalidProjects() throws Exception {
	}

	@Test
	public void testMetricsForProject() throws Exception {
	}
}
