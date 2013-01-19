package org.andreschnabel.jprojectinspector.utilities;

import static org.junit.Assert.*;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class TimelineTapperTest {

	private TimelineTapper tt;

	@Before
	public void setUp() throws Exception {
		this.tt = new TimelineTapper();
	}

	@Test
	public void testTapUniqueProjects() throws Exception {
		List<Project> ups = tt.tapUniqueProjects("Java", 4);
		assertTrue(ups.size() >= 4);
	}

}
