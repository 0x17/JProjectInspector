package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;

import java.util.List;

public class DeltaCalculator {
	
	public static class MetricsDeltas {
		public int dloc, dtloc, dcontribs, dcommits, dissues;
		public String toCsv(Character sep) {
			return "" + dloc + sep + dtloc + sep + dcontribs + sep + dcommits + sep + dissues;
		}
		public static String csvHeader(char c, Character sep) {
			return c + "dloc" + sep + c + "dtloc" + sep + c + "dcontribs" + sep + c + "dcommits" + sep + c + "dissues";
		}
	}
	
	public static class Deltas {
		public String user;
		public MetricsDeltas testing, bugs;
		public static String csvHeader(Character sep) {
			return "user" + sep + MetricsDeltas.csvHeader('t', sep) + sep + MetricsDeltas.csvHeader('b', sep) + "\n";
		}
		public String toCsvLine(Character sep) {
			return user + sep + testing.toCsv(sep) + sep + bugs.toCsv(sep) + "\n";
		}
	}
	
	public static ProjectWithResults metricsForProject(final Project p, List<ProjectWithResults> pml) {
		Predicate<ProjectWithResults> isProj = new Predicate<ProjectWithResults>() {
			@Override
			public boolean invoke(ProjectWithResults obj) {
				return obj.project.equals(p);
			}
		};
		return Func.find(isProj, pml);
	}

	public static List<Deltas> calculateDeltas(List<ResponseProjects> rpl, final List<ProjectWithResults> pml) {
		Transform<ResponseProjects, Deltas> responseProjToDeltas = new Transform<ResponseProjects, DeltaCalculator.Deltas>() {
			@Override
			public Deltas invoke(ResponseProjects rp) {
				ProjectWithResults highBugMetrics = metricsForProject(new Project(rp.user, rp.highestBugCount), pml);
				ProjectWithResults lowBugMetrics = metricsForProject(new Project(rp.user, rp.lowestBugCount), pml);
				ProjectWithResults highTestMetrics = metricsForProject(new Project(rp.user, rp.mostTested), pml);
				ProjectWithResults lowTestMetrics = metricsForProject(new Project(rp.user, rp.leastTested), pml);
				
				Deltas d = new Deltas();
				
				d.user = rp.user;
				
				d.testing = new MetricsDeltas();
				if(highTestMetrics != null && lowTestMetrics != null) {
					/*d.testing.dcommits = highTestMetrics.numCommits - lowTestMetrics.numCommits;
					d.testing.dcontribs = highTestMetrics.numContribs - lowTestMetrics.numContribs;
					d.testing.dloc = highTestMetrics.linesOfCode - lowTestMetrics.linesOfCode;
					d.testing.dtloc = highTestMetrics.testLinesOfCode - lowTestMetrics.testLinesOfCode;
					d.testing.dissues = highTestMetrics.numIssues - lowTestMetrics.numIssues;*/
				}
				
				d.bugs = new MetricsDeltas();
				if(highBugMetrics != null && lowBugMetrics != null) {
					/*d.bugs.dcommits = highBugMetrics.numCommits - lowBugMetrics.numCommits;
					d.bugs.dcontribs = highBugMetrics.numContribs - lowBugMetrics.numContribs;
					d.bugs.dloc = highBugMetrics.linesOfCode - lowBugMetrics.linesOfCode;
					d.bugs.dtloc = highBugMetrics.testLinesOfCode - lowBugMetrics.testLinesOfCode;
					d.bugs.dissues = highBugMetrics.numIssues - lowBugMetrics.numIssues;*/
				}
				
				return d;
			}
		};
		return Func.map(responseProjToDeltas, rpl);
	}

}
