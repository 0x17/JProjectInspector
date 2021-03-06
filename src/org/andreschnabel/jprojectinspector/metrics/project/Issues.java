package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.githubapi.GitHubHelpers;
import org.andreschnabel.pecker.helpers.Helpers;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bestimmt Anzahl der Issues im Tracker von gegebenen Projekt per Scraping von GitHub.
 */
public class Issues implements IOnlineMetric {

	public static int getNumberOfIssues(Project p) throws Exception {
		String pageSrc = Helpers.loadUrlIntoStr("https://github.com/" + p.owner + "/" + p.repoName);
		if(!pageSrc.contains("repo_issues")) { // issues disabled
			return 0;
		}
		Pattern pat = Pattern.compile("Issues <span class='counter'>([0-9]+)</span>");
		Matcher m = pat.matcher(pageSrc);
		if(m.find()) {
			return Integer.valueOf(m.group(1));
		} else {
			GitHubClient ghc = GitHubHelpers.authenticate();
			RepositoryService repoService = new RepositoryService(ghc);
			IssueService issueService = new IssueService();
			Repository repo = repoService.getRepository(p.owner, p.repoName);
			List<Issue> issues = issueService.getIssues(repo, null);
			return issues.size();
		}
	}

	@Override
	public String getName() {
		return "NumIssues";
	}

	@Override
	public String getDescription() {
		return "Total number of issues in issue tracker of project. Scraped from the project profile.";
	}

	@Override
	public Category getCategory() {
		return Category.Scraping;
	}

	@Override
	public double measure(Project p) throws Exception {
		return getNumberOfIssues(p);
	}
}
