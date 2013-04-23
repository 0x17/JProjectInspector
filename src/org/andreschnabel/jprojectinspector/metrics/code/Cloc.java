package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.IBinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cloc implements IOfflineMetric {

	@Override
	public String getName() {
		return "LinesOfCode";
	}

	@Override
	public String getDescription() {
		return "Lines of code using 'cloc'. Code lines. More info on http://cloc.sourceforge.net/";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return ClocResult.accumResults(determineLinesOfCode(repoRoot)).codeLines;
	}

	public static int sumOfLinesOfCodeForFiles(List<File> files) {
		return Func.reduce(new IBinaryOperator<File, Integer>() {
			@Override
			public Integer invoke(Integer accum, File srcFile) {
				try {
					List<ClocResult> results = Cloc.determineLinesOfCode(srcFile.getParentFile(), srcFile.getName());
					if(results.size() >= 1) {
						accum += ClocResult.accumResults(results).codeLines;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				return accum;
			}
		}, 0, files);
	}

	public static List<ClocResult> determineLinesOfCode(File path) throws Exception {
		return determineLinesOfCode(path, path.getPath());
	}

	public static List<ClocResult> determineLinesOfCode(File rootPath, String targetName) throws Exception {
		List<ClocResult> clocResults = new LinkedList<ClocResult>();

		String out;
		if(Helpers.runningOnUnix()) {
			out = ProcessHelpers.monitorProcess(rootPath, Config.CLOC_PATH, "-xml", targetName);
		} else {
			out = ProcessHelpers.monitorProcess(rootPath, Config.PERL_PATH, Config.absoluteClocPath(), "-xml", targetName);
		}

		Pattern langLine = Pattern.compile("<language name=\"([^\"]+)\" files_count=\"(\\d+?)\" blank=\"(\\d+?)\" comment=\"(\\d+?)\" code=\"(\\d+?)\" />");
		Matcher m = langLine.matcher(out);
		while(m.find()) {
			ClocResult cr = new ClocResult();
			cr.language = m.group(1);
			cr.fileCount = Integer.valueOf(m.group(2));
			cr.blankLines = Integer.valueOf(m.group(3));
			cr.commentLines = Integer.valueOf(m.group(4));
			cr.codeLines = Integer.valueOf(m.group(5));

			// XML is used for configs way too often.
			if(!cr.language.toLowerCase().equals("xml"))
				clocResults.add(cr);
		}
		return clocResults;
	}

}
