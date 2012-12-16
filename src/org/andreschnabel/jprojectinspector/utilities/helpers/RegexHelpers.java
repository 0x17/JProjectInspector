package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.utilities.StringPair;
import org.andreschnabel.jprojectinspector.utilities.StringTriple;

public class RegexHelpers {

	public static List<String> batchMatchOneGroup(String regex, String input) throws Exception {
		if(!regex.contains("(") || !regex.contains(")"))
			throw new Exception("Regex must contain at least one group: " + regex);
		
		List<String> result = new LinkedList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 1) {
				result.add(m.group(1));
			}
		}
		return result;
	}

	public static List<StringPair> batchMatchTwoGroups(String regex, String input) {
		List<StringPair> result = new LinkedList<StringPair>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 2) {
				result.add(new StringPair(m.group(1), m.group(2)));
			}
		}
		return result;
	}

	public static List<StringTriple> batchMatchThreeGroups(String regex, String input) {
		List<StringTriple> result = new LinkedList<StringTriple>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 3) {
				result.add(new StringTriple(m.group(1), m.group(2), m.group(3)));
			}
		}
		return result;
	}

}
