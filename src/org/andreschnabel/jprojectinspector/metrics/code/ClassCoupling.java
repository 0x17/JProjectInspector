package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.utilities.Helpers;

import java.io.File;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ClassCoupling {

	private Map<String, File> classInFile = new HashMap<String, File>();

	public void reset() {
		classInFile.clear();;
	}

	public float getAverageCoupling(File root) throws Exception {
		List<String> classNames = listClassNamesInProject(root);
		int classCount = classNames.size();
		int couplingCount = 0;
		for(String className : classNames) {
			couplingCount += referencedClasses(className).size();
		}
		return (float)couplingCount/classCount;
	}

	private List<String> listClassNamesInFile(File f) throws Exception {
		String srcStr = Helpers.readEntireFile(f);
		List<String> clsNames = Helpers.batchMatchOneGroup("class [A-Za-z0-9]+", srcStr);

		for(String className : clsNames) {
			if(!classInFile.containsKey(className))
				classInFile.put(className, f);
		}

		return clsNames;
	}

	public List<String> listClassNamesInProject(File rootDir) throws Exception {
		List<String> clsNames = new LinkedList<String>();
		if(rootDir.isDirectory()) {
			for(File f : rootDir.listFiles()) {
				clsNames.addAll(listClassNamesInProject(f));
			}
			return clsNames;
		}
		else {
			return listClassNamesInFile(rootDir);
		}
	}
	
	public List<String> referencedClasses(String className) throws Exception {
		String classCode = getCodeOfClass(className);
		List<String> refCls = new LinkedList<String>();

		for(String knownClassName : classInFile.keySet()) {
			if(classCode.contains(knownClassName))
				refCls.add(knownClassName);
		}

		return refCls;
	}

	private String getCodeOfClass(String className) throws Exception {
		if(!classInFile.containsKey(className))
			return "";

		File f = classInFile.get(className);
		String sourceStr = Helpers.readEntireFile(f);

		String openStr = "class " + className;
		int beginIndex = sourceStr.indexOf(openStr) + openStr.length();
		// find opening parentheses
		int i;
		for(i=beginIndex; i<sourceStr.length(); i++) {
			if(sourceStr.charAt(i) == '{')
				break;
		}

		// find corresponding closing one
		int parensCounter = 1;
		for(; i<sourceStr.length() && parensCounter != 0; i++) {
			char c = sourceStr.charAt(i);
			if(c == '{') parensCounter++;
			else if(c == '}') parensCounter--;
		}

		return sourceStr.substring(beginIndex, i);
	}

}
