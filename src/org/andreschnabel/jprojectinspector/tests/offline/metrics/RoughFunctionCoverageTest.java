package org.andreschnabel.jprojectinspector.tests.offline.metrics;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class RoughFunctionCoverageTest {
	@Test
	public void testApproxFunctionCoverage() throws Exception {
		Map<String, Float> coverages = RoughFunctionCoverage.approxFunctionCoverage(new File("testdata"));

		for(String lang : coverages.keySet()) {
			float cov = coverages.get(lang);
			Assert.assertTrue(cov >= 0.0f);
			Assert.assertTrue(cov <= 1.0f);
		}
	}
}
