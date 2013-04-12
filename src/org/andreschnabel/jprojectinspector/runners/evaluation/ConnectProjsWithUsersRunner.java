package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.UserProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.SurveyProjectExtractor;
import org.andreschnabel.jprojectinspector.evaluation.survey.UserGuesser;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class ConnectProjsWithUsersRunner {

	public static void main(String[] args) throws Exception {
		connectProjectsWithUsers();
	}

	public static void connectProjectsWithUsers() throws Exception {
		List<ResponseProjects> results = SurveyProjectExtractor.extractProjectsFromResults(new File("data/responses500.csv"));
		UserProjects projs = (UserProjects) XmlHelpers.deserializeFromXml(UserProjects.class, new File("data/userprojects500.xml"));

		for(ResponseProjects rp : results) {
			rp.user = UserGuesser.guessUserWithProjects(rp, projs.usrProjs);
		}

		XmlHelpers.serializeToXml(new ResponseProjectsLst(results), new File("data/responses500.xml"));
	}


	public static void showProjectWithoutUserCount() throws Exception {
		int count = countProjectsWithoutUser();
		System.out.println("Projs without user: " + count);
	}

	public static int countProjectsWithoutUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responses500.xml"));
		Predicate<ResponseProjects> isWithoutUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user == null;
			}
		};
		return ListHelpers.count(isWithoutUser, rpl.responseProjs);
	}

}
