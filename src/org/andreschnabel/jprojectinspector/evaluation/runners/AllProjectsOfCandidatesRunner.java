package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;

import java.io.File;
import java.util.List;

public class AllProjectsOfCandidatesRunner {
	public static void main(String[] args) throws Exception {
		userProjsForCandidates();
	}

	public static void userProjsForCandidates() throws Exception {
		CsvData candidatesData = CsvHelpers.parseCsv(new File("data/KandidatenUmfrage2.csv"));
		List<String> users = candidatesData.getColumn("login");
		List<Project> userProjects = UserScraper.scrapeProjectsOfUsers(users);
		CsvData csv = Project.projectListToCsv(userProjects);
		csv.save(new File("data/KandidatenProjekteUmfrage2.csv"));
	}

}
