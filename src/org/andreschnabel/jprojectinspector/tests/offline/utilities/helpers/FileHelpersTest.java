package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

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
				+ "- [gitective](https://github.com/kevinsawicki/gitective)";
		assertEquals(expContent, content);
	}

	@Test
	public void testRmDir() throws Exception {
		String dirname = Config.DEST_BASE + "testdir";
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
	
	@SuppressWarnings("unused")
	private class MiniClass {	
		public String name;
		public int number;
	}

	@Test
	public void testWriteObjToJsonFile() throws Exception {
		MiniClass obj = new MiniClass();
		obj.name = "Test";
		obj.number = 23;
		FileHelpers.writeObjToJsonFile(obj, "obj.json");
		File f = new File("obj.json");
		assertTrue(f.exists());
		String json = FileHelpers.readEntireFile(f);
		assertEquals("{\n"
			+"  \"name\": \"Test\",\n"
			+"  \"number\": 23\n"
			+"}", json);
		
		f.delete();
	}

	@Test
	public void testListSourceFiles() throws Exception {
		List<String> out = FileHelpers.listJavaSourceFiles(new File(TestCommon.TEST_SRC_DIRECTORY));
		String[] files = new String[] {
			"Points.java", "PointsTest.java"
		};
		AssertHelpers.arrayEqualsLstOrderInsensitive(files, out);
	}

	@Test
	public void testRecursiveCollectSrcFilesListOfStringFile() throws Exception {
		List<File> out = FileHelpers.recursiveCollectJavaSrcFiles(new File(TestCommon.TEST_SRC_DIRECTORY));
		String[] files = new String[] {
			"Points.java", "PointsTest.java"
		};
		Transform<File, String> fileToName = new Transform<File, String>() {
			@Override
			public String invoke(File f) {
				return f.getName();
			}
		};
		List<String> outNames = Func.map(fileToName, out);
		AssertHelpers.arrayEqualsLstOrderInsensitive(files, outNames);
	}
	
	@Test
	public void testListTestFiles() throws Exception {
		List<String> out = FileHelpers.listTestFiles(TestCommon.TEST_SRC_DIRECTORY);
		Assert.assertEquals(1, out.size());
		Assert.assertEquals(TestCommon.TEST_SRC_TEST_FILENAME, out.get(0));
	}
	
	@Test
	public void testRecursivelyCollectTestFiles() {
		List<String> out = new LinkedList<String>();
		FileHelpers.recursivelyCollectTestFiles(new File(TestCommon.TEST_SRC_DIRECTORY), out);
		Assert.assertEquals(1, out.size());
		Assert.assertEquals(TestCommon.TEST_SRC_TEST_FILENAME, out.get(0));
	}
	
	@Test
	public void testListProductFiles() throws Exception {
		List<String> out = FileHelpers.listProductFiles(TestCommon.TEST_SRC_DIRECTORY);
		Assert.assertEquals(1, out.size());
		Assert.assertEquals(TestCommon.TEST_SRC_FILENAME, out.get(0));
	}
	
	@Test
	public void testRecursivelyCollectSrcFiles() {
		List<String> out = new LinkedList<String>();
		FileHelpers.recursivelyCollectProductFiles(new File(TestCommon.TEST_SRC_DIRECTORY), out);
		Assert.assertEquals(1, out.size());
		Assert.assertEquals(TestCommon.TEST_SRC_FILENAME, out.get(0));
	}

	@Test
	public void testExtension() throws Exception {
		Assert.assertEquals("md", FileHelpers.extension(new File("README.md")));
		Assert.assertEquals("", FileHelpers.extension(new File(".gitignore")));
	}
}