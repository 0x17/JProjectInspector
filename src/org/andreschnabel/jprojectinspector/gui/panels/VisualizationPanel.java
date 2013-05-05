package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.visualizations.BoxAndWhisker;
import org.andreschnabel.jprojectinspector.gui.visualizations.IVisualization;
import org.andreschnabel.jprojectinspector.gui.visualizations.VisualizationsRegistry;
import org.andreschnabel.jprojectinspector.gui.windows.FreeChartWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

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

	private final Dimension dim = new Dimension(640, 480);

	public VisualizationPanel(List<String> metricNames, final Map<Project, Double[]> results) {
		Map<String, IVisualization> vis = VisualizationsRegistry.visualizations;
		int numVis = vis.size();

		setLayout(new GridLayout(2+numVis, 1));

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

		JButton boxPlotCombined = new JButton("box and whisker combined");
		boxPlotCombined.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				showCombined("box and whisker");
			}
		});
		add(boxPlotCombined);
	}

	private void showCombined(String visName) {
		BoxAndWhisker bwvis = (BoxAndWhisker) VisualizationsRegistry.visualizations.get(visName);
		String[] metricNamesArray = metricNames.toArray(new String[metricNames.size()]);
		JFreeChart chart = bwvis.visualizeCombined(metricNamesArray, results);
		FreeChartWindow fcw = new FreeChartWindow(chart, dim);
		fcw.setVisible(true);
	}

	private void showVis(String visName) {
		final String metricName = (String) metricNameComboBox.getSelectedItem();
		final int i = metricNames.indexOf(metricName);

		Map<Project, Double> mresults = new HashMap<Project, Double>();
		for(Project p : results.keySet()) {
			mresults.put(p, results.get(p)[i]);
		}

		JFreeChart chart = VisualizationsRegistry.visualizations.get(visName).visualize(metricName, mresults);
		FreeChartWindow fcw = new FreeChartWindow(chart, dim);
		fcw.setVisible(true);
	}
}
