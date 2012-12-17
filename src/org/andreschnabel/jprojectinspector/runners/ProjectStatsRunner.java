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

public class ProjectStatsRunner {
	
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			throw new Exception("Must supply one argument!");
		}
		
		String arg = args[0];
		if(arg.contains("/")) {
			collectForSingleProject(Project.fromString(arg));
		} else if(arg.endsWith(".json")) {
			collectForProjectLst(arg);
		} else {
			throw new Exception("Argument must be project owner/repo or project list json file!");
		}
		
	}
	
	public static void collectForSingleProject(Project p) throws Exception {
		ProjectStatsMeasurer psm = new ProjectStatsMeasurer();
		ProjectDownloader pd = new ProjectDownloader();
		File projectRoot = pd.loadProject(p);
		ProjectStats stats = psm.collectStats(p, projectRoot);
		pd.deleteProject(p);
		FileHelpers.writeObjToJsonFile(stats, "singleStats.json");
	}
	
	public static void collectForProjectLst(String lstFilename) throws Exception {
		ProjectDownloader pd = new ProjectDownloader();
		ProjectStatsMeasurer psm = new ProjectStatsMeasurer();
				
		List<ProjectStats> stats = new LinkedList<ProjectStats>();
		ProjectStatsList psl = new ProjectStatsList(stats);
		
		Gson gson = new Gson();
		ProjectList plist = gson.fromJson(FileHelpers.readEntireFile(new File(lstFilename)), ProjectList.class);
		
		int i=1;
		int nprojects = plist.projects.size();
		
		for(Project p : plist.projects) {
			Helpers.log("Processing project " + i + "/" + nprojects);
			File projectRoot = pd.loadProject(p);
			stats.add(psm.collectStats(p, projectRoot));
			pd.deleteProject(p);
			i++;
		}
		
		FileHelpers.writeObjToJsonFile(psl, "stats.json");
	}

}
