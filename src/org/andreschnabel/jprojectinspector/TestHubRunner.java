package org.andreschnabel.jprojectinspector;

import org.andreschnabel.jprojectinspector.evaluation.MetricsCollector;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.utilities.EquationHelpers;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.helpers.RegexHelpers;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Kommandozeilen-Tool für das Testbedarf-Ranking von einer gegebenen CSV-Datei mit GitHub-Projekten.
 * <p>
 * <b>Benutzung:</b>
 * TestHubRunner projectList=projects.csv out=projectsTestingNeedRanking.csv testingNeedEquation=AvgChurnPerRevision
 * </p>
 * <p>
 * Dabei ist projects.csv ein Beispiel für den Dateinamen einer CSV-Datei mit Spalten "owner,repo".
 * Dabei ist projectsTestingNeedRanking.csv ein Beispiel für den Dateinamen der Ausgabedatei. Auch wieder Spalten "owner,repo" aber in Reihenfolge
 * des absteigenden Testbedarfs.
 * Dabei ist AverageChurnPerRevision ein Beispiel für eine Formel für den Testbedarf, welche sich aus Namen von Metriken, welche JProjectInspector implementiert, zusammen setzt.
 * </p>
 */
public class TestHubRunner {

	public static void main(String[] args) throws Exception {
		Map<String, String> pairs = parseArgs(args);
		String projLstPath = pairs.get("projectList");
		String outPath = pairs.get("out");
		String eqtn = pairs.get("testingNeedEquation");
		if(projLstPath == null || outPath == null || eqtn == null) {
			throw new Exception("Usage: TestHubRunner projectList=projects.csv out=projectsTestingNeedRanking.csv testingNeedEquation=AverageChurnPerRevision");
		}

		List<Project> projects = Project.projectListFromCsv(CsvHelpers.parseCsv(new File(projLstPath)));
		final Map<Project, Double> projectToTestingNeed = initProjectToTestingNeedMap(projects, eqtn);
		Collections.sort(projects, new Comparator<Project>() {
			@Override
			public int compare(Project p1, Project p2) {
				Double tn1 = projectToTestingNeed.get(p1);
				Double tn2 = projectToTestingNeed.get(p2);
				if(tn1 > tn2) {
					return -1;
				} else if(tn1 == tn2) {
					return 0;
				} else {
					return 1;
				}
			}
		});

		CsvData orderedCsv = Project.projectListToCsv(projects);
		orderedCsv.save(new File(outPath));
	}

	private static Map<Project, Double> initProjectToTestingNeedMap(List<Project> projects, String eqtn) {
		List<String> metricNames = gatherMetricNamesFromEquation(eqtn);
		String[] metricNamesArray = metricNames.toArray(new String[metricNames.size()]);
		Map<Project, Double> projectToTestingNeed = new HashMap<Project, Double>(projects.size());

		for(Project p : projects) {
			Double[] results = MetricsCollector.gatherMetricsForProject(metricNames, p);
			ProjectWithResults pwr = new ProjectWithResults(p, metricNamesArray, results);
			double tn = EquationHelpers.evaluateEquationForProjectWithResultBindings(pwr, eqtn);
			projectToTestingNeed.put(p, tn);
			Helpers.log("" + p + " has testing need of " + tn);
		}

		return projectToTestingNeed;
	}

	private static List<String> gatherMetricNamesFromEquation(String equation) {
		List<String> substrs = RegexHelpers.batchMatchOneGroup("(\\w+)", equation);
		List<String> result = new LinkedList<String>();
		for(String metricName : MetricsRegistry.listAllMetrics()) {
			if(substrs.contains(metricName)) {
				result.add(metricName);
			}
		}
		return result;
	}

	private static Map<String, String> parseArgs(String[] args) throws Exception {
		Map<String, String> keyValMap = new HashMap<String, String>();
		Pattern keyValPattern = Pattern.compile("([^=]+)=([^=]+)");
		for(String arg : args) {
			Matcher m = keyValPattern.matcher(arg);
			if(m.matches()) {
				String key = m.group(1);
				String val = m.group(2);
				keyValMap.put(key, val);
			} else {
				throw new Exception("Malformed arguments! Every argument needs have \"key=value\" format!");
			}
		}
		return keyValMap;
	}

}
