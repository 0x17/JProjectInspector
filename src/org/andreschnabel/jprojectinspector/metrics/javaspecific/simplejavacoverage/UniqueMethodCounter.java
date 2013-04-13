package org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueMethodCounter {

	public void determineUniqueMethodsInProject(File root, List<String> projectMethodNames) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles())
				determineUniqueMethodsInProject(f, projectMethodNames);
		} else {
			if(root.getName().endsWith(".java")) {
				String srcStr = FileHelpers.readEntireFile(root);
				if(!UnitTestDetector.isJavaSrcTest(srcStr, root.getName())) {
					determineUniqueMethodsInSrcStr(srcStr, projectMethodNames);
				}
			}
		}
	}

	public void determineUniqueMethodsInSrcStr(String srcStr, List<String> projectMethodNames) {
		String methodRegex = "(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)";
		Pattern p = Pattern.compile(methodRegex);
		Matcher m = p.matcher(srcStr);
		while(m.find()) {
			if(m.groupCount() == 3) {
				String methodName = m.group(3);
				Helpers.addToLstNoDups(projectMethodNames, methodName);
			}
		}
	}

}