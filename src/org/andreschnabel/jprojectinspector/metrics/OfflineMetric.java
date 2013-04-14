package org.andreschnabel.jprojectinspector.metrics;

import java.io.File;

public interface OfflineMetric {

	public String getName();
	public float measure(File repoRoot) throws Exception;

}
