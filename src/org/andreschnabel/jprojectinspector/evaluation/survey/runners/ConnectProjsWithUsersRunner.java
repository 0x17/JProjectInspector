package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.SurveyProjectExtractor;
import org.andreschnabel.jprojectinspector.evaluation.survey.UserGuesser;
import org.andreschnabel.jprojectinspector.metrics.survey.BugCountEstimation;
import org.andreschnabel.jprojectinspector.metrics.survey.TestEffortEstimation;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class ConnectProjsWithUsersRunner {

	public static void main(String[] args) throws Exception {
		connectProjectsWithUsers();
	}

	public static void connectProjectsWithUsers() throws Exception {
		final List<ResponseProjects> results = SurveyProjectExtractor.extractProjectsFromResults(new File("data/responses500.csv"));
		List<Project> projs = Project.projectListFromCsv(CsvHelpers.parseCsv(new File("data/userprojects500.csv")));

		for(ResponseProjects rp : results) {
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);
		}

		//XmlHelpers.serializeToXml(new ResponseProjectsLst(results), new File("data/responseswithuser500.xml"));
		String[] headers = new String[] {"user",
				TestEffortEstimation.LEAST_TESTED_HEADER,
				TestEffortEstimation.MOST_TESTED_HEADER,
				BugCountEstimation.LOWEST_BUG_COUNT_HEADER,
				BugCountEstimation.HIGHEST_BUG_COUNT_HEADER};
		Transform<ResponseProjects, String[]> respProjToRow = new Transform<ResponseProjects, String[]>() {
			@Override
			public String[] invoke(ResponseProjects rp) {
				return new String[] {rp.user,
						CsvHelpers.escapeIfComma(rp.leastTested),
						CsvHelpers.escapeIfComma(rp.mostTested),
						CsvHelpers.escapeIfComma(rp.lowestBugCount),
						CsvHelpers.escapeIfComma(rp.highestBugCount)};
			}
		};
		CsvData respProjs = CsvData.fromList(headers, respProjToRow, results);
		respProjs.save(new File("data/userrespprojs.csv"));

		Predicate<Project> hasResponse = new Predicate<Project>() {
			@Override
			public boolean invoke(final Project p) {
				Predicate<ResponseProjects> isProject = new Predicate<ResponseProjects>() {
					@Override
					public boolean invoke(ResponseProjects rps) {
						return rps.toProjectList().contains(p);
					}
				};
				return Func.contains(isProject, results);
			};
		};
		List<Project> projsWithResp = Func.filter(hasResponse, projs);

		CsvData projsWithRespCsv = Project.projectListToCsv(projsWithResp);
		projsWithRespCsv.save(new File("data/projsWithResponses.csv"));
	}


	public static void showProjectWithoutUserCount() throws Exception {
		int count = countProjectsWithoutUser();
		System.out.println("Projs without user: " + count);
	}

	public static int countProjectsWithoutUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		Predicate<ResponseProjects> isWithoutUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user == null;
			}
		};
		return Func.count(isWithoutUser, rpl.responseProjs);
	}

}
