
package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CodeMetrics {
	private String basePath;

	public void setLocSettings(LocSettings locSettings) {
		this.locSettings = locSettings;
	}
	private LocSettings locSettings;

	class LocSettings {
		boolean ignoreEmptyLines;
		boolean ignoreComments;
		boolean ignoreDeclarations;
	}

	public CodeMetrics(String basePath) {
		this.basePath = basePath;
		if(basePath.endsWith("/"))
			basePath += "/";
	}

	private String combinedPath(String relPath) {
		return basePath + relPath;
	}

	public int sumLinesOfCodeForDir(String relPath) throws Exception {
		File root = new File(combinedPath(relPath));
		if(!root.isDirectory())
			throw new Exception("Root path should be directory!");

		int sum = 0;
		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				sum += sumLinesOfCodeForDir(f.getPath());
			}
			else if(f.getName().endsWith(".java")) {
				sum += countLinesOfCodeForFile(f.getPath());
			}
		}
		return sum;
	}

	public int maxMcCabeForDir(String relPath) throws Exception {
		File root = new File(combinedPath(relPath));
		if(!root.isDirectory())
			throw new Exception("Root path should be directory!");

		int curMax = 0;
		int val;
		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				val = maxMcCabeForDir(f.getPath());
			}
			else if(f.getName().endsWith(".java")) {
				val = mcCabeForFile(f.getPath());
			}
			else continue;

			if(val > curMax) curMax = val;
		}

		return curMax;
	}

	public int countLinesOfCodeForFile(String relPath) throws IOException {
		FileReader fr = new FileReader(combinedPath(relPath));
		BufferedReader br = new BufferedReader(fr);

		boolean inMultiLineComment = false;

		while(br.ready()) {
			String line = br.readLine().trim();

			if(locSettings.ignoreDeclarations && !line.contains(";") && !isControlStructureInString(line)) continue;

			if(inMultiLineComment) {
				if(line.contains("*/")) {
					inMultiLineComment = false;
					if(line.endsWith("*/")) continue;
				}
				else continue;
			}

			if(locSettings.ignoreEmptyLines && line.length() == 0) continue; // empty line
			if(locSettings.ignoreComments && line.startsWith("//")) continue; // single line comments
			if(locSettings.ignoreComments && line.startsWith("/*") && !line.contains("*/")) { // multiline comment
				inMultiLineComment = true;
			}
		}

		br.close();
		fr.close();

		return 0;
	}

	public int mcCabeForFile(String relPath) throws IOException {
		FileReader fr = new FileReader(combinedPath(relPath));
		BufferedReader br = new BufferedReader(fr);

		int ctr = 0;
		String[] keywords = new String[] { "if", "else", "while", "for", "switch", "case"};

		while(br.ready()) {
			String line = br.readLine().trim();
			ctr += Helpers.countOccurencesOfWords(line, keywords);
		}

		br.close();
		fr.close();
		return ctr;
	}

	private boolean isControlStructureInString(String line) {
		return Helpers.strContainsOneOf(line, new String[] {"if", "else", "do", "while", "switch"});
	}

	public class CodeSummary {
		public int sumLoc;
		public int maxMcCabe;
	}

	public CodeSummary getSummary() throws Exception {
		CodeSummary summary = new CodeSummary();
		summary.sumLoc = 0;//sumLinesOfCodeForDir(".");
		summary.maxMcCabe = 0;//maxMcCabeForDir(".");
		return summary;
	}
}
