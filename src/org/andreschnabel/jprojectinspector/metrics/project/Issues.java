package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Issues {
	
	public int getNumberOfIssues(Project p) throws Exception {
		GitHubClient ghc = GitHelpers.authenticate();
		RepositoryService repoService = new RepositoryService(ghc);
		IssueService issueService = new IssueService();
		Repository repo = repoService.getRepository(p.owner, p.repoName);
		List<Issue> issues = issueService.getIssues(repo, null);
		return issues.size();
	}

}
