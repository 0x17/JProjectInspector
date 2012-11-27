
package org.andreschnabel.jprojectinspector.metrics.impls;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.Helpers;

public class GitMetrics implements AutoCloseable {
	
	private final static String BASE_URL = "https://github.com/";
	private String projName;
	private String destPath;
	
	public GitMetrics(String owner, String repoName) {
		this(owner, repoName, "/tmp/");
	}
	
	public GitMetrics(String owner, String repoName, String destPath) {
		Helpers.system("git clone " + BASE_URL + owner + "/" + repoName + " " + destPath);
		projName = repoName;
		this.destPath = destPath;
	}
	
	@Override
	public void close() throws Exception {
		Helpers.system("rm -rf " + destPath + projName);
	}
	
	public List<String> listSourceFiles() {
		File dir = new File(destPath+projName);
		List<String> srcFilenames = new LinkedList<String>();
		recursiveCollectSrcFiles(srcFilenames, dir);		
		return srcFilenames;
	}
	
	private static void recursiveCollectSrcFiles(List<String> srcFilenames, File dir) {
		for(File f : dir.listFiles()) {
			if(f.isDirectory()) {
				recursiveCollectSrcFiles(srcFilenames, f);
			} else {
				String name = f.getName();
				if(name.endsWith(".java"))
					srcFilenames.add(name);
			}
		}
	}

}
