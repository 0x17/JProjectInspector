package org.andreschnabel.jprojectinspector.metrics.code;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

public class Cloc {
	
	private Cloc() {}
	
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
	}	
	
	public static List<ClocResult> determineLinesOfCode(File rootPath) throws Exception {
		List<ClocResult> clocResults = new LinkedList<ClocResult>();
		String out = ProcessHelpers.monitorProcess(rootPath, "perl", "libs/cloc.pl", "-xml", rootPath.getPath());
		Pattern langLine = Pattern.compile("<language name=\"([^\"]+)\" files_count=\"(\\d+?)\" blank=\"(\\d+?)\" comment=\"(\\d+?)\" code=\"(\\d+?)\" />");
		Matcher m = langLine.matcher(out);
		while(m.find()) {
			ClocResult cr = new ClocResult();
			cr.language = m.group(1);
			cr.blankLines = Integer.valueOf(m.group(2));
			cr.commentLines = Integer.valueOf(m.group(3));
			cr.codeLines = Integer.valueOf(m.group(4));
			clocResults.add(cr);
		}
		return clocResults;
	}
	
	public static void main(String[] args) throws Exception {
		List<ClocResult> results = Cloc.determineLinesOfCode(new File("."));
		for(ClocResult r : results) {
			Helpers.log(r.toString());
		}
	}

}
