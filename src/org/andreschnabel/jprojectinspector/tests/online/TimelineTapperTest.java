package org.andreschnabel.jprojectinspector.tests.online;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.TimelineTapper;
import org.junit.Test;

public class TimelineTapperTest {
	@Test
	public void testTapUniqueProject() throws Exception {
		List<Project> ups = TimelineTapper.tapUniqueProjects("Java", 1);
		assertTrue(ups.size() >= 1);
	}
	
	@Test
	public void testTapUniqueProjectsAtLeastFour() throws Exception {
		List<Project> ups = TimelineTapper.tapUniqueProjects("Java", 4);
		assertTrue(ups.size() >= 4);
	}
}
