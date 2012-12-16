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
		if(args.length != 1 || args[0].endsWith(".json")) {
			throw new Exception("Must supply json file with project list!");
		}
		
		ProjectDownloader pd = new ProjectDownloader();
		ProjectStatsMeasurer psm = new ProjectStatsMeasurer();
				
		List<ProjectStats> stats = new LinkedList<ProjectStats>();
		ProjectStatsList psl = new ProjectStatsList(stats);
		
		Gson gson = new Gson();
		ProjectList plist = gson.fromJson(FileHelpers.readEntireFile(new File("randProjs.json")), ProjectList.class);
		
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
