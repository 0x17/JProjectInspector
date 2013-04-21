package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.List;

public class AvgLocPerSourceFile implements OfflineMetric {

	private static final String[] SRC_EXTENSIONS = new String[] {"java", "js", "rb", "py", "cs",
			"cpp", "c", "pl", "clj", "h", "hpp", "scala", "m", "mm"};

	@Override
	public String getName() {
		return "AvgLocPerSourceFile";
	}

	@Override
	public String getDescription() {
		return "Total number of lines of code of all source files divided by number of those source files.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		Predicate<File> isSrcFile = new Predicate<File>() {
			@Override
			public boolean invoke(File f) {
				String extension = FileHelpers.extension(f);
				return StringHelpers.equalsOneOf(extension, SRC_EXTENSIONS);
			}
		};
		List<File> srcFiles = FileHelpers.filesWithPredicateInTree(repoRoot, isSrcFile);
		int numSourceFiles = srcFiles.size();
		int locSum = Cloc.sumOfLinesOfCodeForFiles(srcFiles);
		return (double)locSum / (double)numSourceFiles;
	}
}
