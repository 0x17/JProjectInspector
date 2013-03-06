package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;

public class CandidateFilter {

	private CandidateFilter() {}

	public static List<Candidate> filterCandidates(List<Candidate> candidates) throws Exception {
		List<Candidate> result = new LinkedList<Candidate>();
		for(Candidate c : candidates) {
			if(qualifies(c)) {
				result.add(c);
				Helpers.log("Candidate qualifies: " + c);
			}
		}
		return result;
	}

	public static boolean qualifies(Candidate candidate) throws Exception {
		return candidate.repos[0] != null && candidate.repos[1] != null && candidate.repos[2] != null;
	}

}
