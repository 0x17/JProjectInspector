package org.andreschnabel.jprojectinspector.tests.offline;

import java.io.File;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.junit.Test;

public class ConfigTest {

	@Test
	public void testInitializePathsFailed() throws Exception {
		boolean result = Config.initializePathsFailed();
		Assert.assertFalse(result);
	}

	@Test
	public void testPersist() throws Exception {
		File f = new File("test.cfg");
		Config.persist(f);
		Assert.assertTrue(f.exists());
		String content = FileHelpers.readEntireFile(f);
		Assert.assertFalse(content.isEmpty());
		f.delete();		
	}

}
