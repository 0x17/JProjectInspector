package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;

public class CountAllContributorsRunner {
	
	public static void main(String[] args) throws Exception {
		ProjectList plist = ProjectList.fromFile(args[0]);
		int contribCount = 0;
		for(Project p : plist.projects) {
			contribCount += Contributors.countNumContributors(p);
		}
		System.out.println("Total contributor count = " + contribCount);		
	}

}
