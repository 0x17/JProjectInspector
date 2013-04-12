package org.andreschnabel.jprojectinspector.model.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProjectMetricsLst {

	public ProjectMetricsLst(List<ProjectMetrics> results) {
		projectMetrics = results;
	}

	public ProjectMetricsLst() {}

	@XmlElementWrapper(name = "projectmetricslst")
	@XmlElement(name = "projectmetrics")
	public List<ProjectMetrics> projectMetrics;
}
