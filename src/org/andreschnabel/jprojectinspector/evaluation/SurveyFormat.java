package org.andreschnabel.jprojectinspector.evaluation;

public class SurveyFormat {
	public final static String RESPONSIBLE_FOR_QA = "Are you responsible for software quality assurance measures on GitHub projects you maintain?";
	public final static String BUZZWORDS = "Please tick all buzz words that you can explain to a friend.";
	public static final String[] BUZZWORD_ARRAY = new String[] {"TDD", "Unit testing", "xUnit", "Function tests", "Mocking", "IoC"};
	public final static String MOST_TESTED_HEADER = "mosttesteffort";
	public final static String LEAST_TESTED_HEADER = "leasttesteffort";
	public final static String LOWEST_BUG_COUNT_HEADER = "lowbugcount";
	public final static String HIGHEST_BUG_COUNT_HEADER = "highbugcount";
	public static final String ESTIMATION_COLUMN_HEADERS = "user,"+ LEAST_TESTED_HEADER +","+ MOST_TESTED_HEADER +","+ LOWEST_BUG_COUNT_HEADER +","+ HIGHEST_BUG_COUNT_HEADER +",weight";
	public static final String METRIC_RESULTS_COLUMN_HEADERS = "owner,repo,metric1name,...,metricNname";
}
