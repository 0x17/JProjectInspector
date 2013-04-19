package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class VisualizeMetricsRunner {

	public static void main(String[] args) throws Exception {
		//ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("data/metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		final List<ProjectWithResults> pms = ProjectWithResults.fromCsv(CsvHelpers.parseCsv(new File("data/benchmark/metrics500.csv")));
		String out = visualizeMetrics(rpl.responseProjs, pms);
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

	public static String visualizeMetrics(List<ResponseProjects> rpl, List<ProjectWithResults> pml) {
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
				ProjectWithResults metrics = Benchmark.metricsForProject(p, pml);
				userRow.append(toCsvRow(metrics, ',') + ((i+1==plist.size()) ? "" : ","));
			}

			sb.append(userRow.toString() + "\n");
		}

		return sb.toString();
	}

	private static String toCsvRow(ProjectWithResults m, Character sep) {
		return "" + m.get("loc") + sep + m.get("testloc") + sep + m.get("numcommits") + sep + m.get("numcontribs") + sep + m.get("numissues");
	}

}
