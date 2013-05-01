package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.model.Project;

/**
 * Umfrage-Metrik. Gibt für Messung eine Einschätzung zurück.
 */
public interface ISurveyMetric {

	public String getName();
	public String getDescription();

	public Estimation measure(Project p);

}
