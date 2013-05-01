package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.MetricResultsPanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.util.List;

/**
 * Fenster mit Metrikergebnissen.
 * @see MetricResultsPanel
 */
public class MetricResultsWindow extends AbstractWindowWithParent<MetricResultsPanel> {
	
	private static final long serialVersionUID = 1L;

	public MetricResultsWindow(CsvData results, JFrame parentFrame) throws Exception {
		super("Metric Results :: " + results.title, 640, 480, JFrame.DISPOSE_ON_CLOSE, new MetricResultsPanel(results), parentFrame);
	}

	public MetricResultsWindow(List<Project> projects, final List<String> metricNames) {
		super("Metric Results", 640, 480, JFrame.DISPOSE_ON_CLOSE, new MetricResultsPanel(projects, metricNames), null);
	}

	@Override
	public void dispose() {
		if(panel.getBatch() != null) {
			panel.getBatch().dipose();
		}
		super.dispose();
	}
}
