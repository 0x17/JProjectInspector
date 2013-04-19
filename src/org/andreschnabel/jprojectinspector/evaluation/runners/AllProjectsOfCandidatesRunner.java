package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.UserProjectCollector;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import java.io.File;
import java.util.List;

public class AllProjectsOfCandidatesRunner {
	public static void main(String[] args) throws Exception {
		userProjsForCandidates();
	}

	public static void userProjsForCandidates() throws Exception {
		CsvData candidatesData = CsvHelpers.parseCsv(new File("data/candidates500.csv"));
		List<String> users = candidatesData.getColumn("login");
		List<Project> userProjects = UserProjectCollector.userProjectsForUsers(users);
		CsvData csv = Project.projectListToCsv(userProjects);
		csv.save(new File("data/userprojects500.csv"));
	}

}
