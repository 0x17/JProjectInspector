package org.andreschnabel.jprojectinspector.tests.online;

import java.util.List;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.evaluation.survey.LanguageDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Test;

public class LanguageDetectorTest {

	@Test
	public void testLanguagesOfProject() throws Exception {		
		List<String> langs = LanguageDetector.languagesOfProject(new Project("0x17", "JProjectInspector"));
		Assert.assertEquals(1, langs.size());
		Assert.assertEquals("Java", langs.get(0));		
	}

}
