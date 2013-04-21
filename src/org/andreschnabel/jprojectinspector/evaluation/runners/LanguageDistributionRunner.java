package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.LanguageDistribution;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LanguageDistributionRunner {

	public static void main(String[] args) throws Exception {
		showLanguageDistribution();
	}

	public static void showLanguageDistribution() throws Exception {
		List<Project> projs = ResponseProjects.allProjects(getProjectsWithUser());
		Map<String, Integer> distr = LanguageDistribution.determineLanguageDistribution(projs);
		for(String lang : distr.keySet()) {
			Helpers.log(lang + " => " + distr.get(lang));
		}
	}

	public static List<ResponseProjects> getProjectsWithUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		Predicate<ResponseProjects> isWithUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user != null;
			}
		};
		return Func.filter(isWithUser, rpl.responseProjs);
	}

}
