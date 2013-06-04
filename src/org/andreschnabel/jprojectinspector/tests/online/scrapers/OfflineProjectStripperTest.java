package org.andreschnabel.jprojectinspector.tests.online.scrapers;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.scrapers.OfflineProjectStripper;
import org.andreschnabel.jprojectinspector.utilities.JsonHelpers;
import org.andreschnabel.pecker.helpers.AssertHelpers;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.junit.Test;

public class OfflineProjectStripperTest {

	@Test
	public void testStripOfflineProjs() throws Exception {
		ProjectList pl = new ProjectList();
		pl.projects.add(Project.fromString("0x17/JProjectInspector"));
		pl.projects.add(Project.fromString("SteveSanderson/John"));
		final String pltestfname = "testtestplist.txt";
		JsonHelpers.writeObjToJsonFile(pl, pltestfname);
		OfflineProjectStripper.stripOfflineProjs(pltestfname);
		FileHelpers.deleteFile(pltestfname);
		ProjectList pl2 = ProjectList.fromJson("STRIPPED" + pltestfname);
		AssertHelpers.arrayEqualsLstOrderSensitive(new Project[] {Project.fromString("0x17/JProjectInspector")}, pl2.projects);
		FileHelpers.deleteFile("STRIPPED"+pltestfname);		
	}

	@Test
	public void testIsOffline() throws Exception {
		Assert.assertFalse(OfflineProjectStripper.isOffline(Project.fromString("0x17/JProjectInspector")));
		Assert.assertTrue(OfflineProjectStripper.isOffline(Project.fromString("SteveSanderson/John")));
	}

}
