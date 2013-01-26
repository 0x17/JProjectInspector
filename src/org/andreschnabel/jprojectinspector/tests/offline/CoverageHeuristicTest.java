package org.andreschnabel.jprojectinspector.tests.offline;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.parsers.coverage.CoverageHeuristic;
import org.junit.Test;

public class CoverageHeuristicTest {

	@Test
	public void testBuildClassHasTestIndex() throws Exception {
		Map<String, String> classHasTest = CoverageHeuristic.buildClassHasTestIndex(TestCommon.TEST_SRC_DIRECTORY);
		Assert.assertTrue(classHasTest.get(TestCommon.TEST_SRC_FILENAME) != null);
	}
	
	@Test
	public void testListUntestedMethods() throws Exception {
		Map<String, String> hasTestIndex = CoverageHeuristic.buildClassHasTestIndex(TestCommon.TEST_SRC_DIRECTORY);
		List<String> untestedMethods = CoverageHeuristic.listUntestedMethods(TestCommon.TEST_SRC_FILENAME, hasTestIndex);
		Assert.assertTrue(untestedMethods.size() > 0);
	}
	
	@Test
	public void testDetermineCoverage() throws Exception {
		float coverage = CoverageHeuristic.determineCoverage("testdata");
		System.out.println(coverage);
	}
	
	@Test
	public void testDetermineAllMethods() throws Exception {
		List<String> methods = CoverageHeuristic.determineAllMethods(TestCommon.TEST_SRC_DIRECTORY);
		Assert.assertTrue(methods.size() > 0);
	}

}
