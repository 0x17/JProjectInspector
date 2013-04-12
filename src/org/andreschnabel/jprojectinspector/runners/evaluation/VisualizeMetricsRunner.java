package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.evaluation.survey.*;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetrics;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class VisualizeMetricsRunner {

	public static void main(String[] args) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("data/metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responses500.xml"));
		String out = visualizeMetrics(rpl.responseProjs, metrics.projectMetrics);
		FileHelpers.writeStrToFile(out, "data/vis500.csv");
	}

	public static String getCsvHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("user,");
		sb.append("tloc,ttloc,tcommits,tcontribs,tissues,");
		sb.append("Tloc,Ttloc,Tcommits,Tcontribs,Tissues,");
		sb.append("bloc,btloc,bcommits,bcontribs,bissues,");
		sb.append("Bloc,Btloc,Bcommits,Bcontribs,Bissues");
		return sb.toString();
	}

	public static String visualizeMetrics(List<ResponseProjects> rpl, List<ProjectMetrics> pml) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCsvHeader() + "\n");
		for(ResponseProjects rp : rpl) {
			if(rp.user == null) continue;
			List<Project> plist = rp.toProjectListDups();
			if(Benchmark.skipInvalidProjects(pml, plist)) continue;

			StringBuilder userRow = new StringBuilder();
			userRow.append(rp.user + ",");

			for(int i = 0; i < plist.size(); i++) {
				Project p = plist.get(i);
				ProjectMetrics metrics = Benchmark.metricsForProject(p, pml);
				userRow.append(toCsvRow(metrics, ',') + ((i+1==plist.size()) ? "" : ","));
			}

			sb.append(userRow.toString() + "\n");
		}

		return sb.toString();
	}

	private static String toCsvRow(ProjectMetrics m, Character sep) {
		return "" + m.linesOfCode + sep + m.testLinesOfCode + sep + m.numCommits + sep + m.numContribs + sep + m.numIssues;
	}

}
