package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.RoughFunctionCoverage;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;
import java.util.Map;

public class RoughCoverageRunner {

	public static void main(String[] args) throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		List<Project> projs = rpl.allProjects();
		Transform<Project, Map<String, Float>> projToCov = new Transform<Project, Map<String, Float>>() {
			@Override
			public Map<String, Float> invoke(Project p) {
				Map<String, Float> cov = null;
				try {
					File path = ProjectDownloader.loadProject(p);
					cov = RoughFunctionCoverage.approxFunctionCoverage(path);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					try {
						ProjectDownloader.deleteProject(p);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				return cov;
			}
		};
		List<Map<String, Float>> coverageResults = Func.map(projToCov, projs);
		Map<Project, Map<String, Float>> projToResult = Func.zipMap(projs, coverageResults);
		StringBuilder sb = new StringBuilder();
		sb.append("owner,repo,avgcov\n");
		for(Project p : projToResult.keySet()) {
			Map<String, Float> result = projToResult.get(p);
			float avgcov = getAverageCov(result);
			sb.append(p.owner+","+p.repoName+","+avgcov+"\n");
		}
		FileHelpers.writeStrToFile(sb.toString(), "data/approxcov500.csv");
	}

	private static float getAverageCov(Map<String, Float> result) {
		int count = 0;
		float sum = 0.0f;
		for(String lang : result.keySet()) {
			if(result.get(lang) != null && result.get(lang) > 0.0f) {
				count++;
				sum += result.get(lang);
			}
		}
		return sum / (float)count;
	}

}
