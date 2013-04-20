package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.githubapi.GitHubHelpers;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.Date;

public class ProjectAge implements OnlineMetric {

	public static long getProjectAge(Project project) throws Exception {
		GitHubClient ghc = GitHubHelpers.authenticate();
		RepositoryService repoService = new RepositoryService(ghc);
		Repository repo = repoService.getRepository(project.owner, project.repoName);

		Date creationDate = repo.getCreatedAt();

		return (new Date().getTime() - creationDate.getTime());
	}

	@Override
	public String getName() {
		return "projectage";
	}

	@Override
	public String getDescription() {
		return "Get age of project in milliseconds.";
	}

	@Override
	public Category getCategory() {
		return Category.GitHubApi;
	}

	@Override
	public double measure(Project p) throws Exception {
		return getProjectAge(p);
	}
}
