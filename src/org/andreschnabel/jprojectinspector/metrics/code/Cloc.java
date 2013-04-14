package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cloc implements OfflineMetric {

	@Override
	public String getName() {
		return "loc";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return ClocResult.accumResults(determineLinesOfCode(repoRoot)).codeLines;
	}

	public static List<ClocResult> determineLinesOfCode(File path) throws Exception {
		return determineLinesOfCode(path, path.getPath());
	}

	public static class ClocResult {		
		public String language;
		public int fileCount;
		public int blankLines;
		public int commentLines;
		public int codeLines;

		@Override
		public String toString() {
			return "ClocResult [language=" + language + ", fileCount=" + fileCount + ", blankLines=" + blankLines
				+ ", commentLines=" + commentLines + ", codeLines=" + codeLines + "]";
		}

		public static ClocResult accumResults(List<ClocResult> results) {
			ClocResult accum = new ClocResult();
			accum.language = "All";
			for(ClocResult result : results) {
				accum.codeLines += result.codeLines;
				accum.blankLines += result.blankLines;
				accum.commentLines += result.commentLines;
				accum.fileCount += result.fileCount;
			}
			return accum;
		}
	}

	public static List<ClocResult> determineLinesOfCode(File rootPath, String targetName) throws Exception {
		List<ClocResult> clocResults = new LinkedList<ClocResult>();

		String out;
		if(Helpers.runningOnUnix()) {
			out = ProcessHelpers.monitorProcess(rootPath, "/usr/local/bin/cloc", "-xml", targetName);
		} else {
			out = ProcessHelpers.monitorProcess(rootPath, "perl", "E:\\cloc.pl", "-xml", targetName);
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
