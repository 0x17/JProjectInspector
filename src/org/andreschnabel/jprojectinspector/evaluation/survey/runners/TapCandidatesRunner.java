package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.CandidateTapper;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.List;

public final class TapCandidatesRunner {

	public static void main(String[] args) throws Exception {
		List<Candidate> candidates = CandidateTapper.tapCandidates(2000);
		CandidateLst cl = new CandidateLst(candidates);
		//XmlHelpers.serializeToXml(cl, new File("data/candidates500.xml"));
		Helpers.log("Wrote " + cl.candidates.size() + " candidates!");
		CsvData csv = cl.toCsv();
		csv.save(new File("data/candidates500.csv"));
		/*CandidateLst clst = (CandidateLst) XmlHelpers.deserializeFromXml(CandidateLst.class, new File("data/candidates500.xml"));
		CsvData csv = clst.toCsv();
		csv.save(new File("data/candidates500.csv"));*/
	}

}
