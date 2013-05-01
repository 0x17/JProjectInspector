package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;

import javax.swing.*;
import java.awt.*;

/**
 * Panel für die Metriken zu einem einzigen Projekt.
 * Zeigt Metriken in einer Gitterförmigen-Anordnung an.
 * Metrik name: Ausprägung.
 */
public class ProjectMetricsPanel extends PanelWithParent {
	private static final long serialVersionUID = 1L;

	public ProjectMetricsPanel(ProjectWithResults projectWithResults) {
		Project p = projectWithResults.project;
		Double[] results = projectWithResults.results;
		String[] headers = projectWithResults.getResultHeaders();

		setLayout(new GridLayout(results.length+1, 2));

		add(new JLabel("Project:"));
		add(new JLabel(""+p));

		for(int i=0; i<headers.length; i++) {
			add(new JLabel(headers[i] + ":"));
			add(new JLabel(""+results[i]));
		}
	}
}
