package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.SourceHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JavaClassCoupling implements OfflineMetric {

	public float getAverageCoupling(File root) throws Exception {
		Map<String, File> classInFile = new HashMap<String, File>();
		List<String> classNames = listClassNamesInProject(root, classInFile);
		if(classNames == null) return 0.0f;
		int classCount = classNames.size();
		int couplingCount = 0;
		for(String className : classNames) {
			couplingCount += referencedClasses(className, classInFile).size();
		}
		return (float) couplingCount / classCount;
	}

	public List<String> listClassNamesInFile(File f, Map<String, File> classInFile) throws Exception {
		String srcStr = FileHelpers.readEntireFile(f);
		List<String> clsNames = RegexHelpers.batchMatchOneGroup("class\\s+(\\w+)", StringHelpers.removeCommentsAndStrings(srcStr));

		for(String className : clsNames) {
			if(!classInFile.containsKey(className))
				classInFile.put(className, f);
		}

		return clsNames;
	}

	public List<String> listClassNamesInProject(File rootDir, Map<String, File> classInFile) throws Exception {
		List<String> clsNames = new LinkedList<String>();
		if(rootDir.isDirectory()) {
			for(File f : rootDir.listFiles()) {
				List<String> result = listClassNamesInProject(f, classInFile);
				if(result != null)
					clsNames.addAll(result);
			}
			return clsNames;
		} else {
			return (rootDir.getName().endsWith(".java")) ? listClassNamesInFile(rootDir, classInFile) : null;
		}
	}

	public List<String> referencedClasses(String className, Map<String, File> classInFile) throws Exception {
		String classCode = getCodeOfClass(className, classInFile);
		List<String> refCls = new LinkedList<String>();

		for(String knownClassName : classInFile.keySet()) {
			if(!knownClassName.equals(className) && classCode.contains(knownClassName))
				refCls.add(knownClassName);
		}

		return refCls;
	}

	public String getCodeOfClass(String className, Map<String, File> classInFile) throws Exception {
		if(!classInFile.containsKey(className))
			return "";

		File f = classInFile.get(className);

		return SourceHelpers.getCodeOfClassInFile(className, f);
	}

	@Override
	public String getName() {
		return "jcoupling";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return getAverageCoupling(repoRoot);
	}
}
