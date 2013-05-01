package org.andreschnabel.jprojectinspector.model;

import com.google.gson.Gson;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Liste von Projekten (evtl. zu bestimmten Schlüsselwort).
 */
public class ProjectList {
	@Deprecated
	public final String keyword;
	public final List<Project> projects;

	public ProjectList(String keyword, List<Project> projects) {
		this.keyword = keyword;
		this.projects = projects;
	}

	public ProjectList() {
		keyword = "none";
		projects = new LinkedList<Project>();
	}
	
	public static ProjectList fromJson(String filename) throws Exception {
		return new Gson().fromJson(FileHelpers.readEntireFile(new File(filename)), ProjectList.class);
	}
}