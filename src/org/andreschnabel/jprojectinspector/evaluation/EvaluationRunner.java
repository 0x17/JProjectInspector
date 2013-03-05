package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class EvaluationRunner {

	public static void main(String[] args) throws Exception {
		List<Candidate> candidates = CandidateTapper.tapCandidates(10);
		List<Candidate> filteredCandidates = CandidateFilter.filterCandidates(candidates);
		CandidateLst cl = new CandidateLst(filteredCandidates);
		XmlHelpers.serializeToXml(cl, new File("kandidaten.xml"));
	}

}
