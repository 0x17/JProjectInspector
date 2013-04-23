package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.VisualizationPanel;
import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class VisualizationWindow extends AbstractWindow<VisualizationPanel> {
	public VisualizationWindow(List<String> metricNames, Map<Project, Double[]> results) {
		super("Visualization", JFrame.DISPOSE_ON_CLOSE, new VisualizationPanel(metricNames, results));
	}
}
