package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.jprojectinspector.model.Project;
import org.jfree.chart.JFreeChart;

import java.util.Map;

/**
 * Schnittstelle für eine Visualisierung.
 */
public interface IVisualization {
	/**
	 * Getter für Namen.
	 * @return Name dieser Visualisierung.
	 */
	public String getName();

	/**
	 * Erzeuge Visualisierung für Metrik und Resultate zu Prjojekten.
	 * @param metricName Name der Metrik.
	 * @param results Resultate für Projekte.
	 * @return JFreeChart-Diagramm.
	 */
	public JFreeChart visualize(String metricName, Map<Project, Double> results);
}
