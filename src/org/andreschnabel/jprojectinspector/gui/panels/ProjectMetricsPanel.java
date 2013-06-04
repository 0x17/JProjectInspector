package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.pecker.helpers.GuiHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		setLayout(new GridLayout(results.length+3, 2));

		add(new JLabel("Project:"));
		add(new JLabel(""+p));

		final String link = "https://github.com/" + p.toId();

		add(new JLabel("Link:"));
		add(new JLabel(link));

		for(int i=0; i<headers.length; i++) {
			add(new JLabel(headers[i] + ":"));
			add(new JLabel(""+results[i]));
		}

		add(new JLabel(""));
		JButton openLinkBtn = new JButton("Open in browser");
		openLinkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GuiHelpers.openUrl(link);
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(openLinkBtn);
	}
}
