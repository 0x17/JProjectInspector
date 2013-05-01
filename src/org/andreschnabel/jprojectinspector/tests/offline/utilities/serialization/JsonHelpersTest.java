package org.andreschnabel.jprojectinspector.tests.offline.utilities.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.JsonHelpers;
import org.junit.Test;

public class JsonHelpersTest {

	@Test
	public void testWriteObjToJsonFile() throws Exception {
		Project obj = new Project("0x17", "JProjectInspector");
		JsonHelpers.writeObjToJsonFile(obj, "obj.json");
		File f = new File("obj.json");
		assertTrue(f.exists());
		String json = FileHelpers.readEntireFile(f);
		assertEquals("{\n"
			+"  \"owner\": \"0x17\",\n"
			+"  \"repoName\": \"JProjectInspector\"\n"
			+"}", json);		
		f.delete();
	}

}
