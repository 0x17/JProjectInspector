package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class GitHelpers {

	public static List<String> contribNamesForFile(File f) throws Exception {
		List<String> result = new LinkedList<String>();

		ProcessBuilder pb = new ProcessBuilder("git", "log", "--follow", "--pretty=format:%an", "--", f.getName());
		pb.directory(f.getParentFile());

		Process p = pb.start();
		InputStreamReader isr = new InputStreamReader(p.getInputStream());

		p.waitFor();

		StringBuilder output = new StringBuilder();
		int c;
		while((c = isr.read()) != -1) {
			output.append((char) c);
		}

		String[] names = output.toString().split("\n");
		for(String name : names) {
			if(name != null && !name.isEmpty()) {
				if(!result.contains(name)) {
					result.add(name);
				}
			}
		}

		return result;
	}

}
