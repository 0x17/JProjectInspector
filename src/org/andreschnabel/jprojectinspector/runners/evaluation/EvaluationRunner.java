package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;

public final class EvaluationRunner {

	public static void main(String[] args) throws Exception {
		/*CandidateLst oldclst = (CandidateLst)XmlHelpers.deserializeFromXml(CandidateLst.class, new File("data/candidates250.xml"));
		List<Candidate> candidates = CandidateTapper.tapCandidates(500, oldclst.candidates);
		CandidateLst cl = new CandidateLst(candidates);
		XmlHelpers.serializeToXml(cl, new File("data/candidates500.xml"));
		Helpers.log("Wrote " + cl.candidates.size() + " candidates!");*/
		CandidateLst clst = (CandidateLst) XmlHelpers.deserializeFromXml(CandidateLst.class, new File("data/candidates500.xml"));
		CsvData csv = clst.toCsv();
		csv.save(new File("data/candidates500.csv"));
	}

}
