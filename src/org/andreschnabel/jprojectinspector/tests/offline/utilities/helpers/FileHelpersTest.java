package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
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
		String expContent = "# JProjectInspector\n" +
				"\n" +
				"Gather data from GitHub projects to determine testing need.\n" +
				"\n" +
				"**License:** BSD\n" +
				"\n" +
				"## Dependencies\n" +
				"**Third party libraries:**\n" +
				"[ASM](http://asm.ow2.org/)\n" +
				"[org.eclipse.egit.github.core](http://www.eclipse.org/egit/)\n" +
				"[google-gson](https://code.google.com/p/google-gson/)\n" +
				"[JCommon](http://www.jfree.org/jcommon/)\n" +
				"[JFreeChart](http://www.jfree.org/jfreechart/)\n" +
				"[iText](http://itextpdf.com/)\n" +
				"\n" +
				"**Third party tools:**\n" +
				"[CLOC](http://cloc.sourceforge.net/)\n" +
				"[git](http://git-scm.com/)\n" +
				"\n" +
				"*Omnia sub specie aeternitatis.*\n";
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
		assertEquals(2, FileHelpers.recursivelyCountFilesWithExtension(new File("dummydata"), "java"));
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
		ITransform<File, String> fileToName = new ITransform<File, String>() {
			@Override
			public String invoke(File f) {
				return f.getName();
			}
		};
		List<String> outNames = Func.map(fileToName, out);
		AssertHelpers.arrayEqualsLstOrderInsensitive(files, outNames);
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

	@Test
	public void testFoldersInTree() throws Exception {
		List<File> folders = FileHelpers.foldersInTree(new File("data"));
		File[] expectedFolders = new File[] {
				new File("data/benchmark"),
				new File("data/oldxml"),
				new File("data/old")
		};
		AssertHelpers.arrayEqualsLstOrderInsensitive(expectedFolders, folders);
	}
}
