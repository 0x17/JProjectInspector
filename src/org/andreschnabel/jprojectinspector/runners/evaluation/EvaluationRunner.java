package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.evaluation.CandidateTapper;
import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public final class EvaluationRunner {

	public static void main(String[] args) throws Exception {
		CandidateLst oldclst = (CandidateLst)XmlHelpers.deserializeFromXml(CandidateLst.class, new File("candidates250.xml"));
		List<Candidate> candidates = CandidateTapper.tapCandidates(500, oldclst.candidates);
		CandidateLst cl = new CandidateLst(candidates);
		XmlHelpers.serializeToXml(cl, new File("candidates500.xml"));
		Helpers.log("Wrote " + cl.candidates.size() + " candidates!");
	}

}
