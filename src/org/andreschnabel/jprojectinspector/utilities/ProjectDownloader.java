package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProjectDownloader {
	
	private ProjectDownloader() {}

	public static File loadProject(Project p) throws Exception {
		if(!isProjectOnline(p)) {
			return null;
		}
		String destPath = Config.DEST_BASE + p.repoName;
		ProcessHelpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		File f = new File(destPath);
		return f.exists() ? f : null;
	}

	public static boolean isProjectOnline(Project p) {
		String projHtml;
		try {
			projHtml = Helpers.loadHTMLUrlIntoStr("https://github.com/"+p.owner+"/"+p.repoName);
		} catch(Exception e) {
			return false;
		}
		return projHtml != null && !projHtml.contains("<title>Page not found &middot; GitHub</title>");
	}

	public static void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		FileHelpers.rmDir(destPath);
	}

	public static void loadProjects(ProjectList plist) throws Exception {
		int ctr=1;
		int numProjs = plist.projects.size();
		for(Project p : plist.projects) {
			Helpers.log("Loading " + p + " (" + (ctr++) + "/" + numProjs + ")"); 
			String destPath = preloadPath(p);
			if(FileHelpers.exists(destPath)) {
				Helpers.log("Skipping...");
				continue;
			}
			ProcessHelpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		}
	}

	public static void revertProjectsToDate(File rootPath, ProjectList plist, String date) throws Exception {
		for(Project project : plist.projects) {
			String sha1 = shaFromLatestCommitAtDate(rootPath, date);
			ProcessHelpers.monitorProcess(rootPath, "git", "reset", "--hard", sha1);
		}
	}

	public static String shaFromLatestCommitAtDate(File repoPath, String date) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "log", "-1", "-until=" + date);
		Pattern commitRegex = Pattern.compile("commit ([0-9a-zA-Z]{40})");
		String[] lines = out.split("\n");
		Matcher commitMatcher = commitRegex.matcher(lines[0]);
		if(commitMatcher.find()) {
			return commitMatcher.group(1);
		} else {
			throw new Exception("Malformed git log output!");
		}
	}
	
	public static String preloadPath(Project p) {
		return Config.DEST_BASE + p.owner.toLowerCase() + StringHelpers.capitalizeFirstLetter(p.repoName.toLowerCase());
	}
	
	public static Project projectFromFolderName(String folder) {
		int i;
		for(i=0; i<folder.length(); i++) {
			char c = folder.charAt(i);
			if(Character.isUpperCase(c)) {
				break;
			}
		}
		String owner = folder.substring(0, i);
		String repo = Character.toLowerCase(folder.charAt(i)) + folder.substring(i+1);
		return new Project(owner, repo);
	}
	
	public static void deleteEmtpyPreloads() throws Exception {
		File b = new File(Config.DEST_BASE);
		for(File f : b.listFiles()) {
			if(f.isDirectory() && f.listFiles().length == 0) {
				Helpers.log("Empty folder: " + f.getName());
				FileHelpers.deleteDir(f);
			}
		}
	}

	public static File[] getPreloadPaths() {
		File bf = new File(Config.DEST_BASE);
		return bf.listFiles();
	}
	
	public static ProjectList getPreloadProjs() {
		File[] paths = getPreloadPaths();
		ProjectList plist = new ProjectList();
		for(File path : paths) {
			plist.projects.add(projectFromFolderName(path.getName()));
		}
		return plist;
	}

}
