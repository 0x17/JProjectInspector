package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHubHelpers;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.Date;

public class ProjectAge {

	public static long getProjectAge(Project project) throws Exception {
		GitHubClient ghc = GitHubHelpers.authenticate();
		RepositoryService repoService = new RepositoryService(ghc);
		Repository repo = repoService.getRepository(project.owner, project.repoName);

		Date creationDate = repo.getCreatedAt();
		long delta = (new Date().getTime() - creationDate.getTime());

		return delta;
	}
}
