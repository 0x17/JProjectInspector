package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.LanguageDistribution;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.serialization.CsvHelpers;
import org.andreschnabel.pecker.serialization.XmlHelpers;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LanguageDistributionRunner {

	public static void main(String[] args) throws Exception {
		showLanguageDistribution();
	}

	public static void showLanguageDistribution() throws Exception {
		//List<Project> projs = ResponseProjects.allProjects(getProjectsWithUser());
		List<Project> projs = Project.projectListFromCsv(CsvHelpers.parseCsv(new File("data/benchmark/MetricResultsUmfragenCombined.csv")));
		Map<String, Integer> distr = LanguageDistribution.determineLanguageDistribution(projs);
		for(String lang : distr.keySet()) {
			Helpers.log(lang + " => " + distr.get(lang));
		}
	}

	public static List<ResponseProjects> getProjectsWithUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		IPredicate<ResponseProjects> isWithUser = new IPredicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user != null;
			}
		};
		return Func.filter(isWithUser, rpl.responseProjs);
	}

}
