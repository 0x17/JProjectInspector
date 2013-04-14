package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrontStats {

	public static class RoughCommits implements OnlineMetric {
		@Override
		public String getName() {
			return "nroughcommits";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).ncommits;
		}
	}

	public static class Branches implements OnlineMetric {
		@Override
		public String getName() {
			return "nbranches";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).nbranches;
		}
	}

	public static class Forks implements OnlineMetric {

		@Override
		public String getName() {
			return "nforks";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).nforks;
		}
	}

	public static class Stars implements OnlineMetric {
		@Override
		public String getName() {
			return "nstars";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).nstars;
		}
	}

	public static class Issues implements OnlineMetric {
		@Override
		public String getName() {
			return "nissues2";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).nissues;
		}
	}

	public static class PullRequests implements OnlineMetric {
		@Override
		public String getName() {
			return "npullrequests";
		}

		@Override
		public float measure(Project p) throws Exception {
			return statsForProject(p).npullreqs;
		}
	}


	public int ncommits;
	public int nbranches;

	public int nforks;
	public int nstars;

	public int nissues;
	public int npullreqs;

	public static FrontStats statsForProject(Project p) throws Exception {
		FrontStats stats = new FrontStats();

		String html = Helpers.loadHTMLUrlIntoStr("https://github.com/" + p.owner + "/" + p.repoName);
		if(html.contains("<title>Page not found &middot; GitHub</title>"))
			return stats;

		stats.ncommits = getNumberOfCommits(html);
		stats.nbranches = getNumberOfBranches(html);
		stats.nforks = getNumberOfForks(html);
		stats.nstars = getNumberOfStars(html);
		stats.nissues = getNumberOfIssues(html);
		stats.npullreqs = getNumberOfPullRequests(html);

		return stats;
	}

	public static int extractNumWithRegex(String html, String regex) {
		Pattern prPattern = Pattern.compile(regex);
		Matcher m = prPattern.matcher(html);
		if(m.find())
			return Integer.valueOf(m.group(1));
		else return 0;
	}

	private final static String NLINESPACECRAP = "\n?\\s*";

	public static int getNumberOfPullRequests(String html) {
		return extractNumWithRegex(html, "highlight=\"repo_pulls\">Pull Requests <span class='counter'>(\\d+)</span></a></li>");
	}

	public static int getNumberOfIssues(String html) {
		return extractNumWithRegex(html, "repo_issues\">Issues <span class='counter'>(\\d+)</span>");
	}

	public static int getNumberOfStars(String html) {
		return extractNumWithRegex(html, "stargazers\">"+NLINESPACECRAP+"(\\d+)"+NLINESPACECRAP+"</a>");
	}

	public static int getNumberOfCommits(String html) {
		return extractNumWithRegex(html, "/commits/master\">\\s*<span class=\"mini-icon mini-icon-history tooltipped\" title=\"Browse commits for this branch\"></span>\n\\s*(\\d+)\\+? commits\n");
	}

	public static int getNumberOfBranches(String html) {
		return extractNumWithRegex(html, "highlight=\"repo_branches\" rel=\"nofollow\">Branches <span class=\"counter \">(\\d+)</span>");
	}

	public static int getNumberOfForks(String html) {
		return extractNumWithRegex(html, "network\" class=\"social-count\">"+NLINESPACECRAP+"(\\d+)"+NLINESPACECRAP+"</a>");
	}

}
