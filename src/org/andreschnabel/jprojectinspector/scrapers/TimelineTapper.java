package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimelineTapper {
	
	private TimelineTapper() {}
	
	public static List<Project> tapUniqueProjects(String lang, int upToNum) throws Exception {
		List<Project> projs = new LinkedList<Project>();
		List<String> projNames = new LinkedList<String>();

		Pattern p = Pattern.compile("\\{(.*?\"language\":\"" + lang + "\".*?)\\}");
		Pattern q = Pattern.compile("\"owner\":\"([\\w-]+)\"");
		Pattern r = Pattern.compile("\"name\":\"([\\w-]+)\"");

		String lastTimeline = null;

		do {
			Helpers.log("Fetching timeline...");
			String timeline = Helpers.loadUrlIntoStr("https://github.com/timeline.json");

			if(lastTimeline != null && lastTimeline.equals(timeline)) {
				Thread.sleep(1000);
				Helpers.log("No change... repeat!");
				continue;
			}

			if(!timeline.contains("\"language\":\"" + lang + "\"")) {
				Helpers.log("No java...");
				continue;
			}

			Matcher m = p.matcher(timeline);
			while(m.find()) {
				if(m.groupCount() == 1) {
					String repoStr = m.group(1);

					if(repoStr.contains("\"language\":\"" + lang + "\"")) {

						try {
							Matcher n = q.matcher(repoStr);
							n.find();
							String owner = n.group(1);

							n = r.matcher(repoStr);
							n.find();
							String repoName = n.group(1);

							Project proj = new Project(owner, repoName);
							if(!projNames.contains(repoName)) {
								projNames.add(repoName);
								projs.add(proj);
							}

							Helpers.log("Found java project: " + proj);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			lastTimeline = timeline;

		} while(projs.size() < upToNum);

		return projs;
	}

}
