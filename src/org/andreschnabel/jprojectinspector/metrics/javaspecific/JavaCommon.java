package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

public class JavaCommon {

	public static boolean containsNoJavaCode(File path) throws Exception {
		Predicate<File> isJava = new Predicate<File>() {
			@Override
			public boolean invoke(File f) {
				return FileHelpers.extension(f).equals("java");
			}
		};
		return !Func.contains(isJava, FileHelpers.filesInTree(path));
	}

}
