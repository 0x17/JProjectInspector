package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import org.andreschnabel.jprojectinspector.testprevalence.Globals;
import org.andreschnabel.jprojectinspector.testprevalence.ProjectDownloader;
import org.andreschnabel.jprojectinspector.testprevalence.model.Project;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ContributorCounter {

	public int countNumContributors(String repoName) throws Exception {
		File root = new File(Globals.DEST_BASE + repoName);
		if(!root.exists())
			throw new Exception("Check out " + repoName + " first!");

		FileRepository repo = new FileRepository(root);
		PlotWalk revWalk = new PlotWalk(repo);
		ObjectId rootId = repo.resolve(Constants.HEAD);
		RevCommit rootCommit = revWalk.parseCommit(rootId);
		revWalk.markStart(rootCommit);
		PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<PlotLane>();
		plotCommitList.source(revWalk);
		plotCommitList.fillTo(Integer.MAX_VALUE);

		List<String> authors = new LinkedList<String>();

		for(RevCommit commit : revWalk) {
			String authorName = commit.getAuthorIdent().getName();
			if(!authors.contains(authorName))
				authors.add(authorName);
		}

		repo.close();

		return authors.size();
	}

	public static void main(String[] args) throws Exception {
		ProjectDownloader pd = new ProjectDownloader();
		Project p = new Project("0x17", "JProjectInspector");
		pd.loadProject(p);
		ContributorCounter cc = new ContributorCounter();
		System.out.println("Number of contributors: " + cc.countNumContributors("JProjectInspector"));
		pd.deleteProject(p);
	}

}
