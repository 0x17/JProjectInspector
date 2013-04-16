package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOnline;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

public class CountAllContributorsRunner {
	
	public static void main(String[] args) throws Exception {
		ProjectList plist = ProjectList.fromJson(args[0]);
		int contribCount = 0;
		for(Project p : plist.projects) {
			int ncontribs = ContributorsOnline.countNumContributors(p);
			contribCount += ncontribs;
			Helpers.log("Project " + p + " has " + ncontribs + " contributors!");
		}
		Helpers.log("Total contributor count = " + contribCount);		
	}

}
