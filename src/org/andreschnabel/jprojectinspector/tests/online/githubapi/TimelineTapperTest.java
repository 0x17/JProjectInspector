package org.andreschnabel.jprojectinspector.tests.online.githubapi;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.TimelineTapper;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Test;

import java.util.List;

public class TimelineTapperTest {
	@Test
	public void testTapProjects() throws Exception {
		List<Project> projs = TimelineTapper.tapProjects();
		AssertHelpers.listNotEmpty(projs);
	}
}
