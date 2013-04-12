package org.andreschnabel.jprojectinspector.evaluation.survey;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public class SurveyProjectExtractor {
	
	private SurveyProjectExtractor() {}
	
	public static List<ResponseProjects> extractProjectsFromResults(File f) throws Exception {
		return extractProjectsFromResults(FileHelpers.readEntireFile(f));
	}
	
	public static List<ResponseProjects> extractProjectsFromResults(String resultCsv) throws Exception {
		List<ResponseProjects> rprojs = new LinkedList<ResponseProjects>();
		String[] lines = resultCsv.split("\n");
		for(String line : lines) {
			if(beginsWithDate(line)) {
				rprojs.add(extractProjectsFromResultLine(line));
			}
		}
		return rprojs;
	}

	public static ResponseProjects extractProjectsFromResultLine(String line) throws Exception {
		Pattern prefixPattern = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{2}:\\d{2}:\\d{2},(Yes|No),(Yes|No),([^,]+),([^,]+),([^,]+),([^,]+),");
		Matcher prefixMatcher = prefixPattern.matcher(line);
		if(!prefixMatcher.find()) {
			throw new Exception("Malformed csv line!");
		}
		String mostTested = prefixMatcher.group(3);
		String testedLeast = prefixMatcher.group(4);
		String mostBugs = prefixMatcher.group(5);
		String leastBugs = prefixMatcher.group(6);
		return new ResponseProjects(testedLeast, mostTested, leastBugs, mostBugs);
	}

	public static boolean beginsWithDate(String line) {
		Pattern datePattern = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{2}:\\d{2}:\\d{2},");
		return datePattern.matcher(line).find();
	}

}
