package org.andreschnabel.jprojectinspector.tests.online.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.TimelineTapper;
import org.andreschnabel.pecker.helpers.AssertHelpers;
import org.junit.Test;

import java.util.List;

public class TimelineTapperTest {
	@Test
	public void testTapProjects() throws Exception {
		List<Project> projs = TimelineTapper.tapProjects();
		AssertHelpers.listNotEmpty(projs);
	}
}
