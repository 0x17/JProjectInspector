package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.ProjectMetricsPanel;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;

import javax.swing.*;

/**
 * Fenster mit Metrikergebnissen zu einem Projekt.
 * @see ProjectMetricsPanel
 */
public class ProjectMetricsWindow extends AbstractWindow<ProjectMetricsPanel> {
	private static final long serialVersionUID = 1L;

	public ProjectMetricsWindow(ProjectWithResults projectWithResults) {
		super(projectWithResults.project.toString(), JFrame.DISPOSE_ON_CLOSE, new ProjectMetricsPanel(projectWithResults));
	}
}
