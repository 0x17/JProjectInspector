package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.testprevalence.model.Project;
import org.andreschnabel.jprojectinspector.testprevalence.ProjectDownloader;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;

public class ContributorCounter {

	public int countNumContributors(File root) throws Exception {
		if(!root.exists())
			throw new Exception("Check out first!");

		FileRepository repo = new FileRepository(root.getAbsolutePath() + "/.git");
		
		Git git = new Git(repo);
		LogCommand logCmd = git.log();
		Iterable<RevCommit> revWalk = logCmd.call();
		
		/*PlotWalk revWalk = new PlotWalk(repo);
		ObjectId rootId = repo.resolve(Constants.HEAD);
		RevCommit rootCommit = revWalk.parseCommit(rootId);
		revWalk.markStart(rootCommit);
		PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<PlotLane>();
		plotCommitList.source(revWalk);
		plotCommitList.fillTo(Integer.MAX_VALUE);*/

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
		File root = pd.loadProject(p);
		ContributorCounter cc = new ContributorCounter();
		System.out.println("Number of contributors: " + cc.countNumContributors(root));
		pd.deleteProject(p);
	}

}
