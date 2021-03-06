package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.UserGuesser;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;
import org.andreschnabel.pecker.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class ConnectProjsWithUsersRunner {

	public static void main(String[] args) throws Exception {
		/*CsvData data = CsvHelpers.parseCsv(new File("data/Benchmark/WeightedEstimates.csv"));
		List<String> users = data.getColumn("user");
		List<Project> projects = UserScraper.scrapeProjectsOfUsers(users);
		Helpers.log(""+projects.size());*/
		connectProjectsWithUsers();
	}

	public static void connectProjectsWithUsers() throws Exception {
		List<ResponseProjects> responseProjectsList = ResponseProjects.fromCsvFile(new File("data/RohantwortenUmfrage2Kopie.csv"));
		List<Project> projs = Project.projectListFromCsv(CsvHelpers.parseCsv(new File("data/KandidatenProjekteUmfrage2.csv")));

		for(ResponseProjects rp : responseProjectsList) {
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);
			Helpers.log("Guessed user: " + rp.user);
			rp.simplify();
		}

		responseProjectsList = Func.filter(new IPredicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rps) {
				/*for(Project p : rps.toProjectList()) {
					if(!ProjectDownloader.isProjectOnline(p))
						return false;
				}*/
				return rps.user != null && !Double.isNaN(rps.weight) && rps.weight != 0.0f;
			}
		}, responseProjectsList);

		CsvData respProjs = ResponseProjects.toCsv(responseProjectsList);
		respProjs.save(new File("data/userEstimates.csv"));

		saveListOfProjectsWithResponse(responseProjectsList, projs);
	}

	private static void saveListOfProjectsWithResponse(final List<ResponseProjects> results, List<Project> projs) throws Exception {
		IPredicate<Project> hasResponse = new IPredicate<Project>() {
			@Override
			public boolean invoke(final Project p) {
				IPredicate<ResponseProjects> isProject = new IPredicate<ResponseProjects>() {
					@Override
					public boolean invoke(ResponseProjects rps) {
						return rps.toProjectList().contains(p);
					}
				};
				return Func.contains(isProject, results);
			}
		};
		List<Project> projsWithResp = Func.filter(hasResponse, projs);

		CsvData projsWithRespCsv = Project.projectListToCsv(projsWithResp);
		projsWithRespCsv.save(new File("data/projsWithResponses.csv"));
	}


	public static void showProjectWithoutUserCount() throws Exception {
		int count = countProjectsWithoutUser();
		Helpers.log("Projs without user: " + count);
	}

	public static int countProjectsWithoutUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		IPredicate<ResponseProjects> isWithoutUser = new IPredicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user == null;
			}
		};
		return Func.count(isWithoutUser, rpl.responseProjs);
	}

}
