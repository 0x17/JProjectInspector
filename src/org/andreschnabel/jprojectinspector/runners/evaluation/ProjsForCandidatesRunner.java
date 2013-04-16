package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.evaluation.UserProjectCollector;
import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.model.survey.UserProject;
import org.andreschnabel.jprojectinspector.model.survey.UserProjects;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class ProjsForCandidatesRunner {
	public static void main(String[] args) throws Exception {
		userProjsForCandidates();
	}

	public static void userProjsForCandidates() throws Exception {
		CandidateLst clst = (CandidateLst) XmlHelpers.deserializeFromXml(CandidateLst.class, new File("data/candidates500.xml"));
		Transform<Candidate, String> candidateToUser = new Transform<Candidate, String>() {
			@Override
			public String invoke(Candidate c) {
				return c.login;
			}
		};
		List<String> users = ListHelpers.map(candidateToUser, clst.candidates);
		List<UserProject> userProjects = UserProjectCollector.userProjectsForUsers(users);
		XmlHelpers.serializeToXml(new UserProjects(userProjects), new File("data/userprojects500.xml"));
	}

}
