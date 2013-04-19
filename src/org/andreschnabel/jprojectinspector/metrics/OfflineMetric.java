package org.andreschnabel.jprojectinspector.metrics;

import java.io.File;

public interface OfflineMetric {

	public String getName();
	public String getDescription();

	public double measure(File repoRoot) throws Exception;

}
