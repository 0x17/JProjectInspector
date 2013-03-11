package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.prototype.TestingNeed;

public final class TestingNeedRunner {

	public static void main(String[] args) throws Exception {
		TestingNeed.determineTestingNeedForProject(TestCommon.THIS_PROJECT);
	}

}
