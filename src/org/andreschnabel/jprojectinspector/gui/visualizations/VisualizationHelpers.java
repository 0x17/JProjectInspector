package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;

import java.util.*;

public class VisualizationHelpers {
	public static List<Project> sortProjectKeysByValAsc(final Map<Project, Double> results) {
		List<Project> projectsSortedByValAsc = new LinkedList<Project>(results.keySet());
		Collections.sort(projectsSortedByValAsc, new Comparator<Project>() {
			@Override
			public int compare(Project p1, Project p2) {
				double p1val = results.get(p1);
				double p2val = results.get(p2);
				if (p1val < p2val || Double.isNaN(p1val)) {
					return -1;
				} else if (p1val == p2val) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return projectsSortedByValAsc;
	}
}
