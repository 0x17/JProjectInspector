package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.UserGuesser;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class ConnectProjsWithUsersRunner {

	public static void main(String[] args) throws Exception {
		connectProjectsWithUsers();
	}

	public static void connectProjectsWithUsers() throws Exception {
		List<ResponseProjects> responseProjectsList = ResponseProjects.fromCsvFile(new File("data/responses500.csv"));
		List<Project> projs = Project.projectListFromCsv(CsvHelpers.parseCsv(new File("data/userprojects500.csv")));

		for(ResponseProjects rp : responseProjectsList) {
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);
		}

		responseProjectsList = Func.filter(new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rps) {
				for(Project p : rps.toProjectList()) {
					if(!ProjectDownloader.isProjectOnline(p))
						return false;
				}
				return rps.user != null && !Double.isNaN(rps.weight) && rps.weight != 0.0f;
			}
		}, responseProjectsList);

		CsvData respProjs = ResponseProjects.toCsv(responseProjectsList);
		respProjs.save(new File("data/userEstimates.csv"));

		saveListOfProjectsWithResponse(responseProjectsList, projs);
	}

	private static void saveListOfProjectsWithResponse(final List<ResponseProjects> results, List<Project> projs) throws Exception {
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
