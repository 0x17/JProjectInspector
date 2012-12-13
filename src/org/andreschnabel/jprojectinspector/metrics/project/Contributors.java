package org.andreschnabel.jprojectinspector.metrics.project;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class Contributors {

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
	
	public int countNumTestContributors(File root) throws Exception {
		if(!root.exists())
			throw new Exception("Check out first!");

		FileRepository repo = new FileRepository(root.getAbsolutePath() + "/.git");

		Git git = new Git(repo);
		LogCommand logCmd = git.log();
		Iterable<RevCommit> revWalk = logCmd.call();

		List<String> authors = new LinkedList<String>();

		FileTreeIterator fti = new FileTreeIterator(repo);

		for(RevCommit commit : revWalk) {
			String authorName = commit.getAuthorIdent().getName();
			if(!authors.contains(authorName)) {
				IndexDiff id = new IndexDiff(repo, commit.getId(), fti);
				boolean isTestCommit = false;
				for(String filename : id.getAdded()) {
					if(filename.toLowerCase().contains("test")) {
						isTestCommit = true;
					}
				}
				if(!isTestCommit) {
					for(String filename : id.getModified()) {
						if(filename.toLowerCase().contains("test")) {
							isTestCommit = true;
						}
					}
				}
				if(isTestCommit)
					authors.add(authorName);
			}
		}

		repo.close();

		return authors.size();
	}

}
