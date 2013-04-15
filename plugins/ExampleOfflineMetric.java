import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

public class ExampleOfflineMetric implements OfflineMetric {

	public String getName() { return "exoffmetric"; }
	public String getDescription() { return "Number of files in repo dir. Example metric implemented as plugin."; }

	public float measure(File repoRoot) throws Exception {
		return FileHelpers.filesInTree(repoRoot).size();
	}

}