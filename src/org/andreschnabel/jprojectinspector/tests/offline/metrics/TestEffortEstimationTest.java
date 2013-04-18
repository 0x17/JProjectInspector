package org.andreschnabel.jprojectinspector.tests.offline.metrics;

import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.metrics.survey.TestEffortEstimation;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Assert;
import org.junit.Test;

public class TestEffortEstimationTest {
	@Test
	public void testMeasure() throws Exception {
		TestEffortEstimation tee = new TestEffortEstimation();
		Assert.assertEquals(Estimation.Highest, tee.measure(new Project("aglover", "exegesis")));
		Assert.assertEquals(Estimation.Lowest, tee.measure(new Project("aglover", "hop-roll")));
	}
}
