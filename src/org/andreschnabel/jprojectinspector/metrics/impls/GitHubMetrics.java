
package org.andreschnabel.jprojectinspector.metrics.impls;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.IGitHubMetrics;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.google.gson.Gson;

public class GitHubMetrics implements IGitHubMetrics {
	private Repository repo;

	private RepositoryService repoService;
	private IssueService issueService;
	private PullRequestService pullReqService;
	private CommitService commitService;

	public GitHubMetrics(String owner, String repoName) throws IOException {
		repoService = new RepositoryService();
		issueService = new IssueService();
		commitService = new CommitService();
		pullReqService = new PullRequestService();

		repo = repoService.getRepository(owner, repoName);
	}

	@Override
	public int getNumberOfContributors() throws IOException {
		return repoService.getContributors(repo, true).size();
	}

	@Override
	public int getTestPopularity() throws IOException {
		List<Contributor> contribs = repoService.getContributors(repo, true);
		int numContribs = contribs.size();
		
		List<User> testingUsers = new LinkedList<User>();
		List<RepositoryCommit> commits = commitService.getCommits(repo);
		for(RepositoryCommit commit : commits) {
			boolean containsTest = false;
			for(CommitFile cf : commit.getFiles()) {
				String filename = cf.getFilename();
				containsTest |= (filename.endsWith("test") || filename.startsWith("test"));
			}
			User author = commit.getAuthor();
			if(containsTest && !testingUsers.contains(author)) {
				testingUsers.add(author);
			}
		}
		
		int numTestContribs = testingUsers.size();		
		
		int result = numTestContribs / numContribs;
		
		return result;
	}

	@Override
	public int getNumberOfIssues() throws IOException {
		List<Issue> issues = issueService.getIssues(repo, null);
		return issues.size();
	}

	@Override
	public int getSelectivity() throws IOException {
		List<PullRequest> pullRequests = pullReqService.getPullRequests(repo, "closed");

		int numMerged = 0;

		for (PullRequest pr : pullRequests) {
			if (pr.isMerged()) numMerged++;
		}

		int numClosed = pullRequests.size();

		return numMerged / numClosed;
	}

	@Override
	public int getCodeFrequency() throws IOException {
		List<RepositoryCommit> commits = commitService.getCommits(repo);

		int added, removed;
		added = removed = 0;

		for (RepositoryCommit commit : commits) {
			for (CommitFile cf : commit.getFiles()) {
				added += cf.getAdditions();
				removed += cf.getDeletions();
			}
		}

		return added - removed;
	}

	@Override
	public long getRepositoryAge() throws IOException {
		Date creatDate = repo.getCreatedAt();
		long delta = (new Date().getTime() - creatDate.getTime());
		return delta;
	}

	public class Summary {
		public int numContribs;
		public int testPopularity;
		public int numIssues;
		public int selectivity;
		public int codeFrequency;
		public long repoAge;
	}

	@Override
	public String toJson() throws IOException {
		Summary s = new Summary();
		s.numContribs = getNumberOfContributors();
		s.testPopularity = getTestPopularity();
		s.numIssues = getNumberOfIssues();
		s.repoAge = getRepositoryAge();
		Gson gson = new Gson();
		return gson.toJson(s);
	}
}
