package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Issues {

	public int getNumberOfIssues(Project p) throws Exception {
		String pageSrc = Helpers.loadUrlIntoStr("https://github.com/"+p.owner+"/"+p.repoName);
		if(!pageSrc.contains("repo_issues")) { // issues disabled
			return 0;
		}
		Pattern pat = Pattern.compile("highlight=\"repo_issues\">Issues <span class='counter'>([0-9]+)</span></a></li>");
		Matcher m = pat.matcher(pageSrc);
		if(m.find()) {
			return Integer.valueOf(m.group(1));
		}
		else {		
			GitHubClient ghc = GitHelpers.authenticate();
			RepositoryService repoService = new RepositoryService(ghc);
			IssueService issueService = new IssueService();
			Repository repo = repoService.getRepository(p.owner, p.repoName);
			List<Issue> issues = issueService.getIssues(repo, null);
			return issues.size();
		}
	}

}
