package org.andreschnabel.jprojectinspector.tests.online;

import java.io.File;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.OfflineProjectStripper;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Test;

import com.google.gson.Gson;

public class OfflineProjectStripperTest {

	@Test
	public void testStripOfflineProjs() throws Exception {
		ProjectList pl = new ProjectList();
		pl.projects.add(Project.fromString("0x17/JProjectInspector"));
		pl.projects.add(Project.fromString("SteveSanderson/John"));
		final String pltestfname = "testtestplist.txt";
		FileHelpers.writeObjToJsonFile(pl, pltestfname);
		OfflineProjectStripper.stripOfflineProjs(pltestfname);
		FileHelpers.deleteFile(pltestfname);
		ProjectList pl2 = new Gson().fromJson(FileHelpers.readEntireFile(new File("STRIPPED"+pltestfname)), ProjectList.class);
		AssertHelpers.arrayEqualsLstOrderSensitive(new Project[] {Project.fromString("0x17/JProjectInspector")}, pl2.projects);
		FileHelpers.deleteFile("STRIPPED"+pltestfname);		
	}

	@Test
	public void testIsOffline() throws Exception {
		Assert.assertFalse(OfflineProjectStripper.isOffline(Project.fromString("0x17/JProjectInspector")));
		Assert.assertTrue(OfflineProjectStripper.isOffline(Project.fromString("SteveSanderson/John")));
	}

}
