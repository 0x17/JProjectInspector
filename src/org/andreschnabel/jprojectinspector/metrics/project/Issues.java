package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Issues {
	
	private IssueService issueService;
	private RepositoryService repoService;
	
	public Issues() {
		repoService = new RepositoryService();
		issueService = new IssueService();
	}
	
	public int getNumberOfIssues(Project p) throws Exception {
		Repository repo = repoService.getRepository(p.owner, p.repoName);
		List<Issue> issues = issueService.getIssues(repo, null);
		return issues.size();
	}

}
