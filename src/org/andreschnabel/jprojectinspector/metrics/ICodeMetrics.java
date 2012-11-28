package org.andreschnabel.jprojectinspector.metrics;

import java.io.IOException;

public interface ICodeMetrics {
	int sumLinesOfCodeForDir(String relPath) throws Exception;

	int maxMcCabeForDir(String relPath) throws Exception;

	int countLinesOfCodeForFile(String relPath) throws IOException;

	int mcCabeForFile(String relPath) throws IOException;
}
