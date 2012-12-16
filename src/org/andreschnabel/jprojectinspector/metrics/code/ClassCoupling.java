package org.andreschnabel.jprojectinspector.metrics.code;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

public class ClassCoupling {

	public float getAverageCoupling(File root) throws Exception {
		Map<String, File> classInFile = new HashMap<String, File>();
		List<String> classNames = listClassNamesInProject(root, classInFile);
		int classCount = classNames.size();
		int couplingCount = 0;
		for(String className : classNames) {
			couplingCount += referencedClasses(className, classInFile).size();
		}
		return (float)couplingCount/classCount;
	}

	public List<String> listClassNamesInFile(File f, Map<String, File> classInFile) throws Exception {
		String srcStr = FileHelpers.readEntireFile(f);
		List<String> clsNames = RegexHelpers.batchMatchOneGroup("class ([A-Za-z0-9]+)", srcStr);

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
				clsNames.addAll(listClassNamesInProject(f, classInFile));
			}
			return clsNames;
		}
		else {
			return listClassNamesInFile(rootDir, classInFile);
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
	
	public String getCodeOfClassInSrcStr(String className, String sourceStr) throws Exception {
		String openStr = "class " + className;
		int beginIndex = sourceStr.indexOf(openStr) + openStr.length();
		// find opening parentheses
		int i;
		for(i=beginIndex; i<sourceStr.length(); i++) {
			if(sourceStr.charAt(i) == '{')
				break;
		}
		
		// don't read { twice
		i++;

		// find corresponding closing one
		beginIndex = i;
		int parensCounter = 1;
		for(; i<sourceStr.length() && parensCounter != 0; i++) {
			char c = sourceStr.charAt(i);
			if(c == '{') parensCounter++;
			else if(c == '}') parensCounter--;
		}
		
		// remove trailing } from result
		i--;

		return sourceStr.substring(beginIndex, i);
	}
	
	public String getCodeOfClassInFile(String className, File f) throws Exception {
		String sourceStr = FileHelpers.readEntireFile(f);
		return getCodeOfClassInSrcStr(className, sourceStr);
	}

	public String getCodeOfClass(String className, Map<String, File> classInFile) throws Exception {
		if(!classInFile.containsKey(className))
			return "";

		File f = classInFile.get(className);
		
		return getCodeOfClassInFile(className, f);
	}

}
