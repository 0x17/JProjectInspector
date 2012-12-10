package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.Collection;

import org.andreschnabel.jprojectinspector.model.Project;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Selectivity {
	
	private static final int MAX_PULL_REQ_PAGES = 1;
	private PullRequestService pullReqService;
	private RepositoryService repoService;
	
	public Selectivity() {
		repoService = new RepositoryService();
		pullReqService = new PullRequestService();
	}
	
	public int getSelectivity(Project p) throws Exception {
		Repository repo = repoService.getRepository(p.owner, p.repoName);
		PageIterator<PullRequest> pullRequestIterator = pullReqService.pagePullRequests(repo, "closed");

		int numMerged = 0;
		int numClosed = 0;

		for(int i=0; i<MAX_PULL_REQ_PAGES && pullRequestIterator.hasNext(); i++) {
			Collection<PullRequest> pullRequests = pullRequestIterator.next();
			for (PullRequest pr : pullRequests) {
				if (pr.isMerged()) numMerged++;
				numClosed++;
			}
		}

		return numClosed == 0 ? 0 : numMerged / numClosed;
	}

}
