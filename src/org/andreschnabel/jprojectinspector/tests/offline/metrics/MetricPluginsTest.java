package org.andreschnabel.jprojectinspector.tests.offline.metrics;

import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class MetricPluginsTest {
	@Test
	public void testLoadOfflineMetricPlugins() throws Exception {
		List<OfflineMetric> metrics = MetricPlugins.loadOfflineMetricPlugins(new File("plugins"));
		AssertHelpers.listNotEmpty(metrics);
	}

	@Test
	public void testLoadOnlineMetricPlugins() throws Exception {
		List<OnlineMetric> metrics = MetricPlugins.loadOnlineMetricPlugins(new File("plugins"));
		AssertHelpers.listNotEmpty(metrics);

	}
}
