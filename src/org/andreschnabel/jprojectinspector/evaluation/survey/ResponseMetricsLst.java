package org.andreschnabel.jprojectinspector.evaluation.survey;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ResponseMetricsLst {

	public ResponseMetricsLst(List<ResponseMetrics> results) {
		responseMetrics = results;
	}

	public ResponseMetricsLst() {}

	@XmlElementWrapper(name = "responsemetricslst")
	@XmlElement(name = "responsemetrics")
	public List<ResponseMetrics> responseMetrics;
}
