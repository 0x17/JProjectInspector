package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Test;

public class FileHelpersTest {

	@Test
	public void testWriteStrToFile() throws Exception {
		String fname = "test.txt";
		String content = "test!";
		FileHelpers.writeStrToFile(content, fname);
		assertEquals(content, FileHelpers.readEntireFile(new File(fname)));
		File f = new File(fname);
		f.delete();
	}

	@Test
	public void testDeleteDir() throws Exception {
		String dirname = "testdir";
		File dir = new File(dirname);
		assertTrue(dir.mkdir());
		assertTrue(dir.exists());
		FileHelpers.deleteDir(dir);
		assertFalse(dir.exists());
	}

	@Test
	public void testReadEntireFile() throws Exception {
		String content = FileHelpers.readEntireFile(new File("README.md"));
		String expContent = "# JProjectInspector\n\n"
				+ "Gather data from GitHub projects to determine factors influencing testing need.\n\n"
				+ "## Dependencies\n"
				+ "- [gson](http://code.google.com/p/google-gson/)\n"
				+ "- [JGit](http://www.eclipse.org/jgit/)\n"
				+ "- [egit-github](https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core)\n"
				+ "- [gitective](https://github.com/kevinsawicki/gitective)\n";
		assertEquals(expContent, content);
	}

	@Test
	public void testRmDir() throws Exception {
		String dirname = "testdir";
		File dir = new File(dirname);
		assertTrue(dir.mkdir());
		assertTrue(dir.exists());
		FileHelpers.rmDir(dirname);
		assertFalse(dir.exists());
	}

	@Test
	public void testRecursivelyCountFilesWithExtension() {
		assertEquals(2, FileHelpers.recursivelyCountFilesWithExtension(new File("testdata"), "java"));
	}

	@Test
	public void testWriteObjToJsonFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testListSourceFiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecursiveCollectSrcFilesListOfStringFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecursiveCollectSrcFilesString() {
		fail("Not yet implemented");
	}

}
