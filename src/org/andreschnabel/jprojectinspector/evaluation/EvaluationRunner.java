package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class EvaluationRunner {

	public static void main(String[] args) throws Exception {
		List<Candidate> candidates = CandidateTapper.tapCandidates(100);
		List<Candidate> extendedCandidates = CandidateTapper.addMostRecentRepoTriples(candidates);
		List<Candidate> filteredCandidates = CandidateFilter.filterCandidates(extendedCandidates);

		CandidateLst cl = new CandidateLst(filteredCandidates);
		XmlHelpers.serializeToXml(cl, new File("candidates.xml"));
	}

}
