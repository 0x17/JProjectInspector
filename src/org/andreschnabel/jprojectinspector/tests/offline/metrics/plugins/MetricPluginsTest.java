package org.andreschnabel.jprojectinspector.tests.offline.metrics.plugins;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.plugins.MetricPlugins;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class MetricPluginsTest {
	@Test
	public void testLoadOfflineMetricPlugins() throws Exception {
		List<IOfflineMetric> metrics = MetricPlugins.loadOfflineMetricPlugins(new File("plugins"));
		AssertHelpers.listNotEmpty(metrics);
	}

	@Test
	public void testLoadOnlineMetricPlugins() throws Exception {
		List<IOnlineMetric> metrics = MetricPlugins.loadOnlineMetricPlugins(new File("plugins"));
		AssertHelpers.listNotEmpty(metrics);

	}
}
