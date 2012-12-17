package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.StringTriple;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

public class CodeFrequency {

	public int countCodeFrequencyForProj(Project project) throws Exception {		
		String cfdStr = Helpers.loadUrlIntoStr("https://github.com/"+project.owner+"/"+project.repoName+"/graphs/code-frequency-data");	
		List<StringTriple> triples = RegexHelpers.batchMatchThreeGroups("\\[([0-9]*),([0-9]*),(-[0-9]*)\\]", cfdStr);
		
		if(triples.size() == 0) return 0;
		
		StringTriple last = triples.get(triples.size()-1);
				
		int linesAdded = Integer.valueOf(last.second);
		int linesRemoved =  Integer.valueOf(last.third);

		return linesAdded - linesRemoved;
	}
}
