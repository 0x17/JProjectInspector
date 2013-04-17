package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.MetricResultsPanel;
import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.*;
import java.util.List;

public class MetricResultsWindow extends AbstractWindow<MetricResultsPanel> {
	
	private static final long serialVersionUID = 1L;

	public MetricResultsWindow(List<Project> projects, final List<String> metricNames) {
		super("Metric results", 640, 480, JFrame.DISPOSE_ON_CLOSE, new MetricResultsPanel(projects, metricNames));
		setAlwaysOnTop(true);
	}

	@Override
	public void dispose() {
		panel.getBatch().dipose();
		super.dispose();
	}
}
