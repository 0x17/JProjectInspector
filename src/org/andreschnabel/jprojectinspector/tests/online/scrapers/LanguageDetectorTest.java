package org.andreschnabel.jprojectinspector.tests.online.scrapers;

import java.util.List;

import org.andreschnabel.jprojectinspector.scrapers.LanguageDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.pecker.helpers.AssertHelpers;
import org.junit.Test;

public class LanguageDetectorTest {

	@Test
	public void testLanguagesOfProject() throws Exception {		
		List<String> langs = LanguageDetector.languagesOfProject(new Project("0x17", "JProjectInspector"));
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[]{"Java", "Perl"}, langs);
	}

}
