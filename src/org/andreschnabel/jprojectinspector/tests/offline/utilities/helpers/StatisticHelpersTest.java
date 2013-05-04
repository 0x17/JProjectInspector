package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.helpers.StatisticHelpers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StatisticHelpersTest {

	private List<Double> sortedNums;

	@Before
	public void setUp() throws Exception {
		sortedNums = Arrays.asList(-200.0, -10.0, 0.0, 1.0, 3.14159265358979323846, 5.0, 10.0, 1000.0);
	}

	@Test
	public void testMinOfSorted() throws Exception {
		Assert.assertEquals(-200.0, StatisticHelpers.minOfSorted(sortedNums), TestCommon.EPSILON);
	}

	@Test
	public void testMaxOfSorted() throws Exception {
		Assert.assertEquals(1000.0, StatisticHelpers.maxOfSorted(sortedNums), TestCommon.EPSILON);
	}

	@Test
	public void testMedianOfSorted() throws Exception {
		Assert.assertEquals(1.0, StatisticHelpers.medianOfSorted(sortedNums), TestCommon.EPSILON);
	}

	@Test
	public void testLowerQuartileOfSorted() throws Exception {
		Assert.assertEquals(-10.0, StatisticHelpers.lowerQuartileOfSorted(sortedNums), TestCommon.EPSILON);
	}

	@Test
	public void testUpperQuartileOfSorted() throws Exception {
		Assert.assertEquals(5.0, StatisticHelpers.upperQuartileOfSorted(sortedNums), TestCommon.EPSILON);
	}

	@Test
	public void testMean() throws Exception {
		Assert.assertEquals(101.1426990816987241548075, StatisticHelpers.mean(sortedNums), TestCommon.EPSILON);
	}
}
