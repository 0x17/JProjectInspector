package org.andreschnabel.jprojectinspector.metrics.code;

import java.io.File;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public class LinesOfCode {

	public int countLocForProj(Project project) throws Exception {
		File root = new File(Config.DEST_BASE+project.repoName);
		return countLocOfDir(root);
	}

	public int countLocOfSrcFile(File srcFile) throws Exception {
		String srcStr = FileHelpers.readEntireFile(srcFile);
		return countLocOfSrcStr(srcStr);
	}

	public int countLocOfSrcStr(String srcStr) throws Exception {
		int counter = 0;
		
		boolean lineContainsCode = false;
		boolean inComment = false;
		
		for(int i=0; i<srcStr.length(); i++) {
			char c = srcStr.charAt(i);
			if(!lineContainsCode && !inComment && c != '\n' && c != '/' && c != '\r' && c != ' ' && c != '\t') {
				lineContainsCode = true;
				continue;
			}
			
			if(c == '\n' && lineContainsCode) {
				counter++;
				lineContainsCode = false;
				continue;
			}
			
			if(inComment) {
				if(c == '*' && i < srcStr.length() - 1 && srcStr.charAt(i+1) == '/') {
					i++;
					inComment = false;
				}
				
				continue;
			}
			
			// this could be the first char of a comment
			if(c == '/' && i < srcStr.length() - 1) {
				char d = srcStr.charAt(i+1);
				if(d == '*') { // opening multiple line comment
					inComment = true;
					i++;
				} else if(d == '/') {
					// skip to end of line
					for(; i < srcStr.length() && srcStr.charAt(i) != '\n'; i++);
					if(lineContainsCode) { counter++; lineContainsCode = false; }
				}
			}
		}
		
		if(!srcStr.endsWith("\n") && lineContainsCode) counter++;
		
		return counter;
	}

	public int countLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countLocOfDir(f);
			}
			return sum;
		} else
			return countLocOfSrcFile(root);
	}

}
