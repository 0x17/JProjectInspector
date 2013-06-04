package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.FileHelpers;

import java.io.File;

/**
 * Gemeinsam genutzte Java-Hilfsfunktionen.
 */
public class JavaCommon {

	public static boolean containsNoJavaCode(File path) throws Exception {
		IPredicate<File> isJava = new IPredicate<File>() {
			@Override
			public boolean invoke(File f) {
				return FileHelpers.extension(f).equals("java");
			}
		};
		return !Func.contains(isJava, FileHelpers.filesInTree(path));
	}

}
