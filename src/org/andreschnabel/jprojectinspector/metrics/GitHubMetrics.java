
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
		List<Contributor> contribs = repoService.getContributors(repo, true);
		int numContribs = contribs.size();
		
		List<User> testingUsers = new LinkedList<User>();
		PageIterator<RepositoryCommit> commitIterator = commitService.pageCommits(repo, 100);
		
		for(int i=0; i<10 && commitIterator.hasNext(); i++) {
			Collection<RepositoryCommit> commits = commitIterator.next();
			
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
		}
		
		int numTestContribs = testingUsers.size();

		return numTestContribs / numContribs;
	}

	public int getNumberOfIssues() throws IOException {
		List<Issue> issues = issueService.getIssues(repo, null);
		return issues.size();
	}

	public int getSelectivity() throws IOException {
		List<PullRequest> pullRequests = pullReqService.getPullRequests(repo, "closed");

		int numMerged = 0;

		for (PullRequest pr : pullRequests) {
			if (pr.isMerged()) numMerged++;
		}

		int numClosed = pullRequests.size();

		return numMerged / numClosed;
	}

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

	public long getRepositoryAge() throws IOException {
		Date creatDate = repo.getCreatedAt();
		long delta = (new Date().getTime() - creatDate.getTime());
		return delta;
	}

	public class GitHubSummary {
		public int numContribs;
		public int testPopularity;
		public int numIssues;
		public int selectivity;
		public int codeFrequency;
		public long repoAge;
	}

	public GitHubSummary getSummary() throws IOException {
		GitHubSummary s = new GitHubSummary();
		s.numContribs = getNumberOfContributors();
		//s.testPopularity = getTestPopularity();
		s.numIssues = getNumberOfIssues();
		s.repoAge = getRepositoryAge();
		return s;
	}

	public String toJson() throws IOException {
		Gson gson = new Gson();
		return gson.toJson(getSummary());
	}
}
