package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.CandidateTapper;
import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.pecker.serialization.CsvData;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class TapCandidatesRunner {

	public static void main(String[] args) throws Exception {
		List<Candidate> oldCandidates = CandidateLst.fromCsv(new File("candidates2000.csv")).candidates;
		List< Candidate > candidates = new LinkedList<Candidate>();
		CandidateLst cl = new CandidateLst(candidates);
		try {
			CandidateTapper.tapCandidates(2000, candidates, oldCandidates);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			CsvData csv = cl.toCsv();
			csv.save(new File("data/candidates2000.csv"));
		}
	}

}
