package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.util.LinkedList;
import java.util.List;

public class ResultVisualizer {

	public final static int LEAST_TESTED = 0;
	public final static int MOST_TESTED = 1;
	public final static int MIN_BUGS = 2;
	public final static int MAX_BUGS = 3;

	public static String resultsToJsArrays(final List<ResponseProjects> rplorig, final List<ProjectMetrics> pml) {
		Predicate<ResponseProjects> notNull = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp != null;
			}
		};
		List<ResponseProjects> rpl = ListHelpers.filter(notNull, rplorig);

		List<Integer> xs = ListHelpers.countUpTo(rpl.size());

		List<List<Integer>> ys = new LinkedList<List<Integer>>();
		for(int i=0; i<4; i++) {
			ys.add(ListHelpers.map(transformForType(i, pml), rpl));
		}

		//normalizeYs(ys);

		return linesFromXsAndYs(xs, ys);
	}

	private static void normalizeYs(List<List<Integer>> ys) {
		for(int i=0; i<ys.get(0).size(); i++) {
			int max = 0;
			for(int j=0; j<ys.size(); j++) {
				int cand = ys.get(j).get(i);
				if(cand > max)
					max = cand;
			}

			for(int j=0; j<ys.size(); j++) {
				int normalized = (int)((ys.get(j).get(i) / (float)max) * 100.0f);
				ys.get(j).set(i, normalized);
			}
		}
	}

	private static String linesFromXsAndYs(List<Integer> xs, List<List<Integer>> ys) {
		StringBuilder sb = new StringBuilder();

		sb.append("var xs = [");
		for(int i=0; i<ys.size(); i++) {
			sb.append("[" + commaSeparatedElems(xs) + "]" + ((i+1<ys.size()) ? "," : "];\n"));
		}

		sb.append("var ys = [");
		for(int i=0; i<ys.size(); i++) {
			sb.append("[" + commaSeparatedElems(ys.get(i)) + "]" + ((i+1<ys.size()) ? "," : "];\n"));
		}

		return sb.toString();
	}

	private static String commaSeparatedElems(List<Integer> nums) {
		BinaryOperator<Integer, String> binOp = new BinaryOperator<Integer, String>() {
			@Override
			public String invoke(String a, Integer b) {
				return a.isEmpty() ? String.valueOf(b) : (a + ", " + b);
			}
		};
		return ListHelpers.reduce(binOp, "", nums);
	}

	public static Transform<ResponseProjects, Integer> transformForType(final int type, final List<ProjectMetrics> pml) {
		return new Transform<ResponseProjects, Integer>() {
			@Override
			public Integer invoke(ResponseProjects rp) {
				String projName = null;
				switch(type) {
					case LEAST_TESTED:
						projName = rp.leastTested;
						break;
					case MOST_TESTED:
						projName = rp.mostTested;
						break;
					case MIN_BUGS:
						projName = rp.lowestBugCount;
						break;
					case MAX_BUGS:
						projName = rp.highestBugCount;
						break;
				}

				final Project p = new Project(rp.user, projName);
				Predicate<ProjectMetrics> isProj = new Predicate<ProjectMetrics>() {
					@Override
					public boolean invoke(ProjectMetrics obj) {
						return obj.project.equals(p);
					}
				};
				ProjectMetrics pm = ListHelpers.find(isProj, pml);
				return pm != null ? pm.linesOfCode : 0;
			}
		};
	}
}
