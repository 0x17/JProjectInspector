package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.CandidateLst;
import org.andreschnabel.jprojectinspector.evaluation.projects.UserProject;
import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjectCollector;
import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class ProjsForCandidatesRunner {
	public static void main(String[] args) throws Exception {
		userProjsForCandidates();
	}

	public static void userProjsForCandidates() throws Exception {
		CandidateLst clst = (CandidateLst) XmlHelpers.deserializeFromXml(CandidateLst.class, new File("candidates500.xml"));
		List<UserProject> userProjects = UserProjectCollector.userProjectsForCandidates(clst.candidates);
		XmlHelpers.serializeToXml(new UserProjects(userProjects), new File("userprojects500.xml"));
	}

}
