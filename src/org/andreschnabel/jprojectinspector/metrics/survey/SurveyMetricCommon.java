package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.evaluation.survey.UserGuesser;
import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;
import java.util.List;

public class SurveyMetricCommon {

	public static Estimation measureCommon(List<String> surveyUsers, File responseCsv, Project p, String minHeader, String maxHeader) {
		try {
			CsvData respWithUser = UserGuesser.assureHasUser(responseCsv, surveyUsers);
			for(int row=0; row<respWithUser.rowCount(); row++) {
				String user = respWithUser.getCellAt(row, "user");
				if(p.owner.equals(user)) {
					String minRepo = respWithUser.getCellAt(row, minHeader);
					if(p.repoName.equals(minRepo)) {
						return Estimation.Lowest;
					}

					String maxRepo = respWithUser.getCellAt(row, maxHeader);
					if(p.repoName.equals(maxRepo)) {
						return Estimation.Highest;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Estimation.None;
	}

}
