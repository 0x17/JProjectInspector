package org.andreschnabel.jprojectinspector.tests.offline;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.SourceHelpers;
import org.junit.Test;

public class SourceHelpersTest {

	private static final String SRC_STR = "package testpackage;"
		+" public class SomeClass { "
		+"public static void main(String[] args) { "
		+"System.out.println(\"Hallo Welt!\"); } }";
	private static final String CLASS_STR = " public static void "
		+"main(String[] args) { "
		+"System.out.println(\"Hallo Welt!\"); } ";
	private static final String METHOD_STR = " System.out.println(\"Hallo Welt!\"); ";
	
	@Test
	public void testListClassNamesInSrcStr() {
		List<String> classNames = SourceHelpers.listClassNamesInSrcStr(SRC_STR);
		Assert.assertEquals(1, classNames.size());
		Assert.assertEquals("SomeClass", classNames.get(0));
	}
	
	@Test
	public void testListMethodNamesInSrcStr() throws Exception {
		List<String> methodNames = SourceHelpers.listMethodNamesInSrcStr(SRC_STR);
		Assert.assertEquals(1, methodNames.size());
		Assert.assertEquals("main", methodNames.get(0));
		
		methodNames = SourceHelpers.listMethodNamesInSrcStr(FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME)));
		Assert.assertEquals(2+2+3+2, methodNames.size());
	}

	@Test
	public void testGetCodeOfClassInSrcStr() throws Exception {
		String result = SourceHelpers.getCodeOfClassInSrcStr("SomeClass", SRC_STR);
		Assert.assertEquals(CLASS_STR, result);
	}

	@Test
	public void testGetCodeOfClassInFile() throws Exception {
		final String testFilename = "testsrc.java";
		FileHelpers.writeStrToFile(SRC_STR, testFilename);
		try {
			Assert.assertEquals(CLASS_STR, SourceHelpers.getCodeOfClassInFile("SomeClass", new File(testFilename)));
		} finally {
			FileHelpers.deleteFile(testFilename);
		}
	}

	@Test
	public void testGetCodeOfMethodInSrcStr() throws Exception {
		Assert.assertEquals(METHOD_STR, SourceHelpers.getCodeOfMethodInSrcStr("main", SRC_STR));
	}

}
