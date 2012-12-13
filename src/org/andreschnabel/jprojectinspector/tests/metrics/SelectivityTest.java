package org.andreschnabel.jprojectinspector.tests.metrics;

import static org.junit.Assert.assertEquals;

import org.andreschnabel.jprojectinspector.metrics.project.Selectivity;
import org.andreschnabel.jprojectinspector.model.Project;
import org.junit.Before;
import org.junit.Test;

public class SelectivityTest {

	private Selectivity sel;

	@Before
	public void setUp() throws Exception {
		this.sel = new Selectivity();
	}

	@Test
	public void testGetSelectivity() throws Exception {
		assertEquals(0, sel.getSelectivity(new Project("0x17", "JProjectInspector")));
	}

}
