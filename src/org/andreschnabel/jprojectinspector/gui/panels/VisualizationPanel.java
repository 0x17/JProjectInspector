package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.visualizations.IVisualization;
import org.andreschnabel.jprojectinspector.gui.visualizations.VisualizationsRegistry;
import org.andreschnabel.jprojectinspector.gui.windows.FreeChartWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Visualisierungspanel.
 *
 * Enthält Buttons zum Öffnen von Diagrammen für gegebene Metrikergebnisse.
 */
public class VisualizationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JComboBox metricNameComboBox;
	private final Map<Project, Double[]> results;
	private final List<String> metricNames;

	public VisualizationPanel(List<String> metricNames, final Map<Project, Double[]> results) {
		Map<String, IVisualization> vis = VisualizationsRegistry.visualizations;
		int numVis = vis.size();

		setLayout(new GridLayout(1+numVis, 1));

		this.metricNames = metricNames;
		this.results = results;

		metricNameComboBox = new JComboBox();
		for(String name : metricNames) {
			metricNameComboBox.addItem(name);
		}
		add(metricNameComboBox);

		for(final String visName : vis.keySet()) {
			JButton btn = new JButton(visName);


			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					showVis(visName);
				}
			});
			add(btn);
		}
	}

	private void showVis(String visName) {
		final String metricName = (String) metricNameComboBox.getSelectedItem();
		int i = metricNames.indexOf(metricName);

		Map<Project, Double> mresults = new HashMap<Project, Double>();
		for(Project p : results.keySet()) {
			mresults.put(p, results.get(p)[i]);
		}
		JFreeChart chart = VisualizationsRegistry.visualizations.get(visName).visualize(metricName, mresults);
		Dimension dim = new Dimension(640, 480);
		FreeChartWindow fcw = new FreeChartWindow(chart, dim);
		fcw.setVisible(true);
	}
}
