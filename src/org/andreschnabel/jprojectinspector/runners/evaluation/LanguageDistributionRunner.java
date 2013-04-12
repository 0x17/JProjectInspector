package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.evaluation.survey.LanguageDistribution;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LanguageDistributionRunner {

	public static void main(String[] args) throws Exception {
		showLanguageDistribution();
	}

	public static void showLanguageDistribution() throws Exception {
		List<Project> projs = LanguageDistribution.respProjectsToProjects(getProjectsWithUser());
		Map<String, Integer> distr = LanguageDistribution.determineLanguageDistribution(projs);
		for(String lang : distr.keySet()) {
			Helpers.log(lang + " => " + distr.get(lang));
		}
	}

	public static List<ResponseProjects> getProjectsWithUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responses500.xml"));
		Predicate<ResponseProjects> isWithUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user != null;
			}
		};
		return ListHelpers.filter(isWithUser, rpl.responseProjs);
	}

}
