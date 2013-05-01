package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.VisualizationPanel;
import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Visualisierungsfenster.
 * @see VisualizationPanel
 */
public class VisualizationWindow extends AbstractWindow<VisualizationPanel> {
	private static final long serialVersionUID = 1L;

	public VisualizationWindow(List<String> metricNames, Map<Project, Double[]> results) {
		super("Visualization", JFrame.DISPOSE_ON_CLOSE, new VisualizationPanel(metricNames, results));
	}
}
