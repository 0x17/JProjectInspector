package org.andreschnabel.jprojectinspector.tests.offline.metrics.survey;

import org.andreschnabel.jprojectinspector.metrics.survey.BugCountEstimation;
import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Assert;
import org.junit.Test;

public class BugCountEstimationTest {
	@Test
	public void testMeasure() throws Exception {
		BugCountEstimation bce = new BugCountEstimation();
		Assert.assertEquals(Estimation.Lowest, bce.measure(new Project("aglover","exegesis")));
		Assert.assertEquals(Estimation.Highest, bce.measure(new Project("aglover", "hop-roll")));
	}
}
