package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.andreschnabel.pecker.helpers.StringHelpers;

import java.io.File;
import java.util.List;

/**
 * Durchschnittliche Anzahl Codezeilen von Quelldateien (enthält auch Tests).
 */
public class AvgLocPerSourceFile implements IOfflineMetric {

	private static final String[] SRC_EXTENSIONS = new String[] {"java", "js", "rb", "py", "cs",
			"cpp", "c", "pl", "clj", "h", "hpp", "scala", "m", "mm"};

	@Override
	public String getName() {
		return "AvgLocPerSourceFile";
	}

	@Override
	public String getDescription() {
		return "Total number of lines of code of all source files divided by number of those source files. Also includes test files.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		IPredicate<File> isSrcFile = new IPredicate<File>() {
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
