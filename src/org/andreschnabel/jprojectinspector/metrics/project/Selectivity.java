package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.githubapi.GitHubHelpers;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.Collection;

public class Selectivity implements OnlineMetric {

	private static final int MAX_PULL_REQ_PAGES = 5;
	private static PullRequestService pullReqService;
	private static RepositoryService repoService;
	private static boolean initialized = false;

	private static void assertInitialized() {
		if(!initialized) {
			// Authenticate to raise rate limit.
			GitHubClient ghc = null;
			try {
				ghc = GitHubHelpers.authenticate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			repoService = new RepositoryService(ghc);
			pullReqService = new PullRequestService(ghc);
			initialized = true;
		}
	}

	public static int getSelectivity(Project p) throws Exception {
		assertInitialized();

		Repository repo = repoService.getRepository(p.owner, p.repoName);
		PageIterator<PullRequest> pullRequestIterator = pullReqService.pagePullRequests(repo, "closed");

		int numMerged = 0;
		int numClosed = 0;

		for(int i = 0; i < MAX_PULL_REQ_PAGES && pullRequestIterator.hasNext(); i++) {
			Collection<PullRequest> pullRequests = pullRequestIterator.next();
			for(PullRequest pr : pullRequests) {
				if(pr.isMerged()) numMerged++;
				numClosed++;
			}
		}

		return numClosed == 0 ? 0 : numMerged / numClosed;
	}

	@Override
	public String getName() {
		return "Selectivity";
	}

	@Override
	public String getDescription() {
		return "Number of merged (accepted) pull requests divided by number of closed pull requests.";
	}

	@Override
	public Category getCategory() {
		return Category.GitHubApi;
	}

	@Override
	public double measure(Project p) throws Exception {
		return getSelectivity(p);
	}
}
