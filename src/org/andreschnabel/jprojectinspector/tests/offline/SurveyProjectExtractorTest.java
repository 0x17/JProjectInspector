package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.evaluation.survey.SurveyProjectExtractor;
import org.junit.Test;

public class SurveyProjectExtractorTest {
	
	private static final String TEST_LINE1 = "3/19/2013 15:06:17,Yes,Yes,JProjectInspector1,KCImageCollector1,JProjectInspector,KCImageCollector,\"TDD, Unit testing, xUnit\",Test entry.,Test Entry.,Yes";
	private static final String TEST_LINE2 = "Often it feels like I get working code faster if I've got tests backing my development.";

	@Test
	public void testExtractProjectsFromResultsFile() {
	}

	@Test
	public void testExtractProjectsFromResultsString() {
	}

	@Test
	public void testExtractProjectsFromResultLine() throws Exception {
		ResponseProjects expectedProjects = new ResponseProjects();
		expectedProjects.mostTested = "JProjectInspector1";
		expectedProjects.leastTested = "KCImageCollector1";
		expectedProjects.highestBugCount = "JProjectInspector";
		expectedProjects.lowestBugCount = "KCImageCollector";
		Assert.assertEquals(expectedProjects, SurveyProjectExtractor.extractProjectsFromResultLine(TEST_LINE1));
	}

	@Test
	public void testBeginsWithDate() {
		Assert.assertTrue(SurveyProjectExtractor.beginsWithDate(TEST_LINE1));
		Assert.assertFalse(SurveyProjectExtractor.beginsWithDate(TEST_LINE2));		
	}

}
