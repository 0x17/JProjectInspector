
package org.andreschnabel.jprojectinspector.obsolete;

import org.andreschnabel.jprojectinspector.obsolete.metrics.CodeMetrics;
import org.andreschnabel.jprojectinspector.obsolete.metrics.GitHubMetrics;
import org.andreschnabel.jprojectinspector.utilities.Helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Launcher {
	private static void printUsage() {
		System.out.println("Usage: owner/repoName \n[-out=outFilename]");
	}

	public static void main(String[] args) throws Exception {
		String outFilename = "summary.json";
		String owner = null, repoName = null;

		for(String arg : args) {
			if(arg.contains("help"))
				printUsage();
			else if(arg.startsWith("out=")) {
				outFilename = arg.split("=")[1];
			}
			else if(arg.contains("/")){
				String[] parts = arg.split("/");
				if(parts.length == 2) {
					owner = parts[0];
					repoName = parts[1];
				}
			}
		}

		if(owner == null || repoName == null) {
			printUsage();
			return;
		}

		writeSummary(owner, repoName, outFilename);
	}

	@SuppressWarnings("unused")
	private static class Summary {		
		GitHubMetrics.GitHubSummary gitHubSummary;
		//GitMetrics.GitSummary gitSummary;
		CodeMetrics.CodeSummary codeSummary;
	}

	private static void writeSummary(String owner, String repoName, String outFilename) throws Exception {
		String destinationPath = "";

		GitHubMetrics ghm = new GitHubMetrics(owner, repoName);
		//GitMetrics gm = new GitMetrics(owner, repoName, destinationPath);
		CodeMetrics cm = new CodeMetrics(destinationPath);

		Summary summary = new Summary();
		summary.gitHubSummary = ghm.getSummary();
		//summary.gitSummary = gm.getSummary();
		summary.codeSummary = cm.getSummary();

		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Helpers.writeStrToFile(gson.toJson(summary), outFilename);

		//gm.close();
	}

}
