package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;

import java.util.List;

/**
 * Berechne Differenzen von Metrikwerten von Projekten mit hohem (Testaufwand bzw. Fehlerzahl) - niedrigem (Testaufwand bzw. Fehlerzahl)
 */
public class DeltaCalculator {
	
	public static ProjectWithResults metricsForProject(final Project p, List<ProjectWithResults> pml) {
		IPredicate<ProjectWithResults> isProj = new IPredicate<ProjectWithResults>() {
			@Override
			public boolean invoke(ProjectWithResults obj) {
				return obj.project.equals(p);
			}
		};
		return Func.find(isProj, pml);
	}

	public static List<Double[]> calculateDeltas(List<ResponseProjects> rpl, final List<ProjectWithResults> pml) {
		ITransform<ResponseProjects, Double[]> responseProjToDeltas = new ITransform<ResponseProjects, Double[]>() {
			@Override
			public Double[] invoke(ResponseProjects rp) {
				ProjectWithResults highBugMetrics = metricsForProject(new Project(rp.user, rp.highestBugCount), pml);
				ProjectWithResults lowBugMetrics = metricsForProject(new Project(rp.user, rp.lowestBugCount), pml);
				ProjectWithResults highTestMetrics = metricsForProject(new Project(rp.user, rp.mostTested), pml);
				ProjectWithResults lowTestMetrics = metricsForProject(new Project(rp.user, rp.leastTested), pml);

				Double[] deltas = new Double[highBugMetrics.results.length];

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
