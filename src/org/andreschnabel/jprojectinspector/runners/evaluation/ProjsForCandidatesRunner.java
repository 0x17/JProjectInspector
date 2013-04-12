package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.model.survey.UserProject;
import org.andreschnabel.jprojectinspector.evaluation.UserProjectCollector;
import org.andreschnabel.jprojectinspector.model.survey.UserProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class ProjsForCandidatesRunner {
	public static void main(String[] args) throws Exception {
		userProjsForCandidates();
	}

	public static void userProjsForCandidates() throws Exception {
		CandidateLst clst = (CandidateLst) XmlHelpers.deserializeFromXml(CandidateLst.class, new File("data/candidates500.xml"));
		List<UserProject> userProjects = UserProjectCollector.userProjectsForCandidates(clst.candidates);
		XmlHelpers.serializeToXml(new UserProjects(userProjects), new File("data/userprojects500.xml"));
	}

}
