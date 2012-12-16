package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Helpers;

public class CodeFrequency {

	public int countCodeFrequencyForProj(Project project) throws Exception {		
		String cfdStr = Helpers.loadUrlIntoStr("https://github.com/"+project.owner+"/"+project.repoName+"/graphs/code-frequency-data");
		Pattern p = Pattern.compile("\\[([0-9]*),([0-9]*),(-[0-9]*)\\]");
		Matcher m = p.matcher(cfdStr);
		int linesAdded = 0;
		int linesRemoved =  0;
		if(m.groupCount() == 3) {
			//long time = Long.valueOf(m.group(1));
			linesAdded += Integer.valueOf(m.group(2));
			linesRemoved += Integer.valueOf(m.group(3));
		} else return 0;

		return linesAdded - linesRemoved;
	}
}
