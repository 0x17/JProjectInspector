package org.andreschnabel.jprojectinspector.prototype;

import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

import java.io.File;

public class TestingNeed {

	public static float determineTestingNeedForProject(Project p) throws Exception {
		float testingNeed = 0;

		try {
			File repoPath = ProjectDownloader.loadProject(p);
			int loc = LinesOfCode.countLocForProj(p);
			int ncontribs = Contributors.countNumContributors(p);
			int ncommits = GitHelpers.numCommits(repoPath);
			String[] commits = GitHelpers.listAllCommits(repoPath);
			GitHelpers.ChurnStats churn = GitHelpers.getChurnStatsBetweenCommits(repoPath, commits[0], commits[commits.length - 1]);
			int totalChurn = churn.getChurnedLoc();

			float contribsPerLoc = ncontribs / (float)loc;
			float commitsPerLoc = ncommits / (float)loc;
			float churnPerLoc = totalChurn / (float)loc;

			testingNeed = contribsPerLoc + commitsPerLoc + churnPerLoc;

		} finally {
			ProjectDownloader.deleteProject(p);
			testingNeed = -1;
		}

		return testingNeed;
	}

}
