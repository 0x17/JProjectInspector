package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;

import java.io.File;
import java.util.List;

public class PurgeInvalidResponses {

	public static void main(String[] args) throws Exception {
		List<ResponseProjects> rps = ResponseProjects.fromPreprocessedCsvFile(new File("data/benchmark/WeightedEstimatesUmfrage2.csv"));
		rps = Func.filter(new IPredicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects r) {
				for(Project p : r.toProjectList()) {
					if(!ProjectDownloader.isProjectOnline(p)) {
						return false;
					}
				}
				return true;
			}
		}, rps);
		ResponseProjects.toCsv(rps).save(new File("data/benchmark/WeightedEstimatesUmfrage2Processed.csv"));
	}

}
