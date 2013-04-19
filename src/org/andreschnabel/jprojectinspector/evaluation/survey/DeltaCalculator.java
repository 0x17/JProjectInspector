package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;

import java.util.List;

/**
 * Calculates differences of metric values from projects with
 * high (testing effort resp. bug count est) - low (testing effort resp. bug count est)
 */
public class DeltaCalculator {
	
	public static ProjectWithResults metricsForProject(final Project p, List<ProjectWithResults> pml) {
		Predicate<ProjectWithResults> isProj = new Predicate<ProjectWithResults>() {
			@Override
			public boolean invoke(ProjectWithResults obj) {
				return obj.project.equals(p);
			}
		};
		return Func.find(isProj, pml);
	}

	public static List<Float[]> calculateDeltas(List<ResponseProjects> rpl, final List<ProjectWithResults> pml) {
		Transform<ResponseProjects, Float[]> responseProjToDeltas = new Transform<ResponseProjects, Float[]>() {
			@Override
			public Float[] invoke(ResponseProjects rp) {
				ProjectWithResults highBugMetrics = metricsForProject(new Project(rp.user, rp.highestBugCount), pml);
				ProjectWithResults lowBugMetrics = metricsForProject(new Project(rp.user, rp.lowestBugCount), pml);
				ProjectWithResults highTestMetrics = metricsForProject(new Project(rp.user, rp.mostTested), pml);
				ProjectWithResults lowTestMetrics = metricsForProject(new Project(rp.user, rp.leastTested), pml);

				Float[] deltas = new Float[highBugMetrics.results.length];

				if(highTestMetrics != null && lowTestMetrics != null) {
					for(int i=0; i<deltas.length; i++) {
						deltas[i] = highTestMetrics.results[i] - lowTestMetrics.results[i];
					}
				}

				if(highBugMetrics != null && lowBugMetrics != null) {
					for(int i=0; i<deltas.length; i++) {
						deltas[i] = highBugMetrics.results[i] - lowBugMetrics.results[i];
					}
				}
				
				return deltas;
			}
		};
		return Func.map(responseProjToDeltas, rpl);
	}

}
