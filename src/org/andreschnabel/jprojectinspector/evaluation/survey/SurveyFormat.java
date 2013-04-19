package org.andreschnabel.jprojectinspector.evaluation.survey;

public class SurveyFormat {
	public final static String RESPONSIBLE_FOR_QA = "Are you responsible for software quality assurance measures on GitHub projects you maintain?";
	public final static String BUZZWORDS = "Please tick all buzz words that you can explain to a friend.";
	public final static String MOST_TESTED_HEADER = "Which of your GitHub projects was tested the most?";
	public final static String LEAST_TESTED_HEADER = "Which of your GitHub projects was tested the least?";
	public final static String LOWEST_BUG_COUNT_HEADER = "Which of your GitHub projects do you suspect to have the smallest number of undetected bugs?";
	public final static String HIGHEST_BUG_COUNT_HEADER = "Which of your GitHub projects do you suspect to have the biggest number of undetected bugs?";
	public static final String[] BUZZWORD_ARRAY = new String[] {"TDD", "Unit testing", "xUnit", "Function tests", "Mocking", "IoC"};
}
