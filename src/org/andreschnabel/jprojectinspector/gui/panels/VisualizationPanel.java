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

public class VisualizationPanel extends JPanel {
	private final List<String> metricNames;
	private final Map<Project, Double[]> results;

	public VisualizationPanel(List<String> metricNames, final Map<Project, Double[]> results) {
		this.metricNames = metricNames;
		this.results = results;

		Map<String, IVisualization> vis = VisualizationsRegistry.visualizations;
		int numVis = vis.size();

		setLayout(new GridLayout(numVis, metricNames.size()+1));

		for(final String visName : vis.keySet()) {
			add(new JLabel(visName));

			int i=0;
			for(final String metricName : metricNames) {
				JButton btn = new JButton(metricName);
				final int finalI = i;
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						Map<Project, Double> mresults = new HashMap<Project, Double>();
						for(Project p : results.keySet()) {
							mresults.put(p, results.get(p)[finalI]);
						}
						JFreeChart chart = VisualizationsRegistry.visualizations.get(visName).visualize(metricName, mresults);
						Dimension dim = new Dimension(640, 480);
						FreeChartWindow fcw = new FreeChartWindow(chart, dim);
						fcw.setVisible(true);
					}
				});
				add(btn);
				i++;
			}
		}
	}
}
