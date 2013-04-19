package org.andreschnabel.jprojectinspector.metrics.code;

import java.util.List;

public class ClocResult {
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
