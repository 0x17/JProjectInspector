package org.andreschnabel.jprojectinspector.runners;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.TimelineTapper;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TimelineTapperRunner {

	public static void main(String[] args) throws Exception {
		TimelineTapper tt = new TimelineTapper();
		List<Project> projs = tt.tapUniqueProjects("Java", 100);
		for(Project p : projs) {
			System.out.println(p);
		}
		ProjectList plst = new ProjectList("none", projs);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(plst);
		FileHelpers.writeStrToFile(json, "randProjs.json");
	}
	
}
