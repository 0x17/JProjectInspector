package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.model.Project;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.Date;

public class ProjectAge {

	public long getProjectAge(Project project) throws Exception {
		Repository repo = new RepositoryService().getRepository(project.owner, project.repoName);
		Date creationDate = repo.getCreatedAt();
		long delta = (new Date().getTime() - creationDate.getTime());
		return delta;
	}
}
