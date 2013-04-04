package org.andreschnabel.jprojectinspector.evaluation.projects;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.evaluation.CandidateLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

public class Runner {
	public static void main(String[] args) throws Exception {		
		CandidateLst clst = (CandidateLst)XmlHelpers.deserializeFromXml(CandidateLst.class, new File("candidates500.xml"));
		List<UserProject> userProjects = UserProjectCollector.userProjectsForCandidates(clst.candidates);		
		XmlHelpers.serializeToXml(new UserProjects(userProjects), new File("userprojects500.xml"));
	}

}
