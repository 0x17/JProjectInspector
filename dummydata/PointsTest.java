package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.junit.Test;

public class PointsTest {

	@Test
	public void test() {
		Position2D p = new Position2D();
		assertNotNull(p.getLocation());
	}

}
