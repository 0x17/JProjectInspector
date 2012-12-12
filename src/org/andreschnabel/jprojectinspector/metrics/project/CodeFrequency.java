package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.model.Project;

public class CodeFrequency {

	public int countCodeFrequencyForProj(Project project) {
		// TODO: Count lines added last week
		int linesAdded = 0;
		// TODO: Count lines removed last week
		int linesRemoved =  0;

		return linesAdded - linesRemoved;
	}
}
