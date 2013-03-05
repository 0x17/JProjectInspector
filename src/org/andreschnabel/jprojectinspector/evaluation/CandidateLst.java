package org.andreschnabel.jprojectinspector.evaluation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CandidateLst {

	@XmlElementWrapper(name = "candidates")
	@XmlElement(name = "candidate")
	public List<Candidate> candidates;

	public CandidateLst(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public CandidateLst() {}
}
