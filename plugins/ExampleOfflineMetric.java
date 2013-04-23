import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

public class ExampleOfflineMetric implements IOfflineMetric {

	public String getName() { return "exoffmetric"; }
	public String getDescription() { return "Number of files in repo dir. Example metric implemented as plugin."; }

	public double measure(File repoRoot) throws Exception {
		return FileHelpers.filesInTree(repoRoot).size();
	}

}