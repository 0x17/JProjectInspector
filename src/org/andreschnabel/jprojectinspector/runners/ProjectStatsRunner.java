package org.andreschnabel.jprojectinspector.runners;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.model.ProjectStats;
import org.andreschnabel.jprojectinspector.model.ProjectStatsList;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.ProjectStatsMeasurer;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import com.google.gson.Gson;

public final class ProjectStatsRunner {
	
	private ProjectStatsRunner() {}
	
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			throw new Exception("Must supply one argument!");
		}

		String arg = args[0];
		if(arg.endsWith(".json")) {
			collectForProjectLst(arg);
		} else if(arg.contains("/")) {
			collectForSingleProject(Project.fromString(arg));
		} else {
			throw new Exception("Argument must be project owner/repo or project list json file!");
		}

	}

	public static void collectForSingleProject(Project p) throws Exception {
		File projectRoot = ProjectDownloader.loadProject(p);
		if(projectRoot != null) {
			try {
				ProjectStats stats = ProjectStatsMeasurer.collectStats(p, projectRoot);
				FileHelpers.writeObjToJsonFile(stats, "singleStats.json");
			} finally {
				ProjectDownloader.deleteProject(p);
			}
		}
	}

	public static void collectForProjectLst(String lstFilename) throws Exception {
		List<ProjectStats> stats = new LinkedList<ProjectStats>();
		ProjectStatsList psl = new ProjectStatsList(stats);

		Gson gson = new Gson();
		ProjectList plist = gson.fromJson(FileHelpers.readEntireFile(new File(lstFilename)), ProjectList.class);

		int i = 1;
		int nprojects = plist.projects.size();

		for(Project p : plist.projects) {
			Helpers.log("Processing project " + i + "/" + nprojects);
			File projectRoot = ProjectDownloader.loadProject(p);
			if(projectRoot != null) {
				try {
					stats.add(ProjectStatsMeasurer.collectStats(p, projectRoot));
				} finally {
					ProjectDownloader.deleteProject(p);
				}
			}
			i++;
		}

		FileHelpers.writeObjToJsonFile(psl, "stats.json");
	}

}
