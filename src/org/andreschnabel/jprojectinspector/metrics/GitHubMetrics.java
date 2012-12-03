
package org.andreschnabel.jprojectinspector.metrics;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.google.gson.Gson;

public class GitHubMetrics {
	private Repository repo;

	private RepositoryService repoService;
	private IssueService issueService;
	private PullRequestService pullReqService;
	private CommitService commitService;

	private static final int MAX_COMMIT_PAGES = 1;
	private static final int MAX_PULL_REQ_PAGES = 1;

	public GitHubMetrics(String owner, String repoName) throws IOException {
		repoService = new RepositoryService();
		issueService = new IssueService();
		commitService = new CommitService();
		pullReqService = new PullRequestService();

		repo = repoService.getRepository(owner, repoName);
	}

	public int getNumberOfContributors() throws IOException {
		return repoService.getContributors(repo, true).size();
	}

	public int getTestPopularity() throws IOException {
		List<Contributor> contributors = repoService.getContributors(repo, true);
		int numContributors = contributors.size();
		
		List<User> testingUsers = new LinkedList<User>();
		PageIterator<RepositoryCommit> commitIterator = commitService.pageCommits(repo, 100);
		
		for(int i=0; i<MAX_COMMIT_PAGES && commitIterator.hasNext(); i++) {
			Collection<RepositoryCommit> commits = commitIterator.next();
			
			for(RepositoryCommit commit : commits) {
				RepositoryCommit actualCommit = commitService.getCommit(repo, commit.getSha());

				boolean containsTest = false;
				for(CommitFile cf : actualCommit.getFiles()) {
					String filename = cf.getFilename();
					containsTest |= (filename.contains("test"));
				}
				
				User author = commit.getAuthor();
				if(containsTest && !testingUsers.contains(author)) {
					testingUsers.add(author);
				}
			}
		}

		return testingUsers.size() / numContributors;
	}

	public int getNumberOfIssues() throws IOException {
		List<Issue> issues = issueService.getIssues(repo, null);
		return issues.size();
	}

	public int getSelectivity() throws IOException {
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

	public int getCodeFrequency() throws IOException {
		PageIterator<RepositoryCommit> commitIterator = commitService.pageCommits(repo);

		int added, removed;
		added = removed = 0;

		for(int i=0; i<MAX_COMMIT_PAGES && commitIterator.hasNext(); i++) {
			Collection<RepositoryCommit> commits = commitIterator.next();

			for(RepositoryCommit commit : commits) {
				RepositoryCommit actualCommit = commitService.getCommit(repo, commit.getSha());
				for(CommitFile cf : actualCommit.getFiles()) {
					added += cf.getAdditions();
					removed += cf.getDeletions();
				}
			}
		}

		return added - removed;
	}

	public long getRepositoryAge() throws IOException {
		Date creationDate = repo.getCreatedAt();
		long delta = (new Date().getTime() - creationDate.getTime());
		return delta;
	}

	public class GitHubSummary {
		public int numContributors;
		public int testPopularity;
		public int numIssues;
		public int selectivity;
		public int codeFrequency;
		public long repoAge;
	}

	public GitHubSummary getSummary() throws IOException {
		GitHubSummary s = new GitHubSummary();
		s.numContributors = getNumberOfContributors();
		s.numIssues = getNumberOfIssues();
		s.repoAge = getRepositoryAge();

		s.testPopularity = getTestPopularity();
		s.selectivity = getSelectivity();
		s.codeFrequency = getCodeFrequency();

		return s;
	}
}
