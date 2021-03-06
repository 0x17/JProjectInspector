package org.andreschnabel.jprojectinspector.tests.offline.metrics.test.coverage;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class RoughFunctionCoverageTest {
	@Test
	public void testApproxFunctionCoverage() throws Exception {
		Map<String, Double> coverages = RoughFunctionCoverage.approxFunctionCoverage(new File("dummydata"));

		for(String lang : coverages.keySet()) {
			Double cov = coverages.get(lang);
			Assert.assertTrue(cov >= 0.0f);
			Assert.assertTrue(cov <= 1.0f);
		}
	}
}
