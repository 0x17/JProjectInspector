package org.andreschnabel.jprojectinspector.tests.offline.metrics.churn;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.churn.AverageChurnPerRevision;
import org.junit.Test;

import java.io.File;

public class AverageChurnPerRevisionTest {
	@Test
	public void testMeasure() throws Exception {
		double avgChurn = new AverageChurnPerRevision().measure(new File("."));
		Assert.assertTrue(avgChurn > 0.0);
	}
}
