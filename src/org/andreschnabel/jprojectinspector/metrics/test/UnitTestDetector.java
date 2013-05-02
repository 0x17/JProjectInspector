package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Erkennung von Modultests.
 */
public class UnitTestDetector implements IOfflineMetric {

	private final static String[] supportedLangs = new String[]{"Java", "Ruby", "C++", "C#", "JavaScript", "Python"};

	private static class IsTestPredicate implements IPredicate<File> {
		@Override
		public boolean invoke(File f) {
			try {
				return isTest(f);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Liefere unterstützte Programmiersprachen für Testerkennung.
	 * @return unterstützte Programmiersprachen für Testerkennung.
	 */
	public static String[] getSupportedLangs() {
		return supportedLangs;
	}

	/**
	 * Prüfe rekursive ob sich in Verzeichnisbaum unter Wurzelverzeichnis Tests befinden.
	 *
	 * Kann sein wenn Codedatei verdächtiges Schlüsselwort enthält.
	 * Kann aber auch sein Unterverzeichnis mit Teilstring "test" vorhanden.
	 *
	 * @param root Wurzelverzeichnis.
	 * @return true, gdw. wahrscheinlich Tests innerhalb von Wurzelverzeichnis.
	 * @throws Exception
	 */
	public static boolean containsTest(File root) throws Exception {
		// contains folder with substring "test"? -> probably has tests
		boolean containsTestFolder = Func.contains(new IPredicate<File>() {
			@Override
			public boolean invoke(File folder) {
				return folder.getName().toLowerCase().contains("test");
			}
		}, FileHelpers.foldersInTree(root));

		if(containsTestFolder) {
			return true;
		}

		// contains file detected as test by is test? -> probably has tests
		return Func.contains(new IsTestPredicate(), FileHelpers.filesInTree(root));
	}

	/**
	 * Prüft, ob gegebene Datei vermutlich ein Modultest ist.
	 *
	 * Kann sein wenn Schlüsselwort enthalten.
	 * Kann sein wenn innerhalb von Verzeichnis mit Namen der Teilstring "test" enthält.
	 *
	 * @param f Datei.
	 * @return true, gdw. Datei vermutlich Modultest ist.
	 * @throws Exception
	 */
	public static boolean isTest(File f) throws Exception {
		// Inside of directory with substring "test" in name? -> prolly test.
		if(f.getAbsolutePath().toLowerCase().contains("test")) {
			return true;
		}

		String filename = f.getName();
		// Source file?
		if(StringHelpers.strEndsWithOneOf(filename, ".java", ".rb", ".py", ".js", ".cpp", ".cs")) {
			String srcStr;
			try {
				srcStr = FileHelpers.readEntireFile(f);
			} catch(Exception e) {
				return false;
			}

			// Contains keyword typical for test written in language with extension? -> prolly test
			if((filename.endsWith(".java") && isJavaSrcTest(srcStr, filename))
					|| (filename.endsWith(".rb") && isRubySrcTest(srcStr, filename))
					|| (filename.endsWith(".py") && isPythonSrcTest(srcStr, filename))
					|| (filename.endsWith(".js") && isJavaScriptSrcTest(srcStr, filename))
					|| (filename.endsWith(".cpp") && isCppSrcTest(srcStr, filename))
					|| (filename.endsWith(".cs") && isCsharpSrcTest(srcStr, filename))) {
				return true;
			}
		}

		// none of the above? prolly not a test.
		return false;
	}

	/**
	 * Liste Dateien innerhalb von Wurzelverzeichnis auf, welche vermutlich ein Modultest sind.
	 * @param root Wurzelverzeichnis.
	 * @return Liste von Dateien innerhalb von root, welche vermutlich Modultest sind.
	 * @throws Exception
	 */
	public static List<File> getTestFiles(File root) throws Exception {
		return FileHelpers.filesWithPredicateInTree(root, new IsTestPredicate());
	}

	/**
	 * Prüfe ob C#-Code vermutlich Modultest ist.
	 * @param srcStr C#-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. C#-Code vermutlich Modultest ist.
	 */
	public static boolean isCsharpSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "[TestFixture]", "[Test]", "[TearDown]", "[Setup]", "using csUnit");
	}

	/**
	 * Prüfe ob C++-Code vermutlich Modultest ist.
	 * @param srcStr C++-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. C++-Code vermutlich Modultest ist.
	 */
	public static boolean isCppSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "CPPUNIT_TEST", "#include <cppunit", "#include<cppunit", "ASSERT_THAT", "EXPECT_THAT");
	}

	/**
	 * Prüfe ob JavaScript-Code vermutlich Modultest ist.
	 * @param srcStr JavaScript-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. JavaScript-Code vermutlich Modultest ist.
	 */
	public static boolean isJavaScriptSrcTest(String srcStr, String filename) {
		Pattern p = Pattern.compile("expect(.+).toBe(.+);");
		Matcher m = p.matcher(srcStr);
		if(m.matches()) return true;
		return StringHelpers.containsOneOf(srcStr, "assertEqual", "registerTestSuite", "strictEqual", "deepEqual", "qunit.js", "expectEq", "expectCall", "buster.js", "buster.");
	}

	/**
	 * Prüfe ob Python-Code vermutlich Modultest ist.
	 * @param srcStr Python-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. Python-Code vermutlich Modultest ist.
	 */
	public static boolean isPythonSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "import doctest", "import unittest", "from unittest", "TestCase", "assertEqual");
	}

	/**
	 * Prüfe ob Ruby-Code vermutlich Modultest ist.
	 * @param srcStr Ruby-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. Ruby-Code vermutlich Modultest ist.
	 */
	public static boolean isRubySrcTest(String srcStr, String filename) {
		if(filename.endsWith("_spec.rb")) return true;
		return StringHelpers.containsOneOf(srcStr, "cucumber", "assertEqual", "require 'test/unit'", "require \"test/unit\"", "Test::Unit", "require \"shoulda\"", "require 'shoulda'", "describe");
	}

	/**
	 * Prüfe ob Java-Code vermutlich Modultest ist.
	 * @param srcStr Java-Code als Zeichenkette.
	 * @param filename Name von Datei, die Code enthält.
	 * @return true, gdw. Java-Code vermutlich Modultest ist.
	 */
	public static boolean isJavaSrcTest(String srcStr, String filename) {
		return StringHelpers.containsOneOf(srcStr, "cucumber", "@Test", "org.junit", "assertEqual");
	}

	/**
	 * Klone Projekt in temporäres lokales Verzeichnis, schaue ob vermutlich Test drin, entferne es wieder.
	 * @param project Projekt in welchem nach Tests geschaut werden soll.
	 * @return true, gdw. vermutlich Test im Projekt.
	 * @throws Exception
	 */
	public static boolean containsTestAndLoad(Project project) throws Exception {
		boolean result = false;
		try {
			File f = ProjectDownloader.loadProject(project);
			result = (f == null) ? false : containsTest(f);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ProjectDownloader.deleteProject(project);
		}
		return result;
	}

	@Override
	public String getName() {
		return "ContainsTest";
	}

	@Override
	public String getDescription() {
		return "Use heuristic to determine if a projects (probably) contains a test (or not). 1.0f iff. (at least one) test. 0.0f otherwise.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return containsTest(repoRoot) ? 1.0f : 0.0f;
	}
}
