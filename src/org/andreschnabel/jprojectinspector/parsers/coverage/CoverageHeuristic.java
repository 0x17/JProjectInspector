package org.andreschnabel.jprojectinspector.parsers.coverage;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.SourceHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;


public class CoverageHeuristic {

	public static Map<String, String> buildClassHasTestIndex(String rootPath) throws Exception {		
		List<String> productFiles = FileHelpers.listProductFiles(rootPath);
		List<String> testFiles = FileHelpers.listTestFiles(rootPath);
		
		Map<String, String> hasTestIndex = new HashMap<String, String>();
		
		for(String productPath : productFiles) {
			String productFilename = new File(productPath).getName();
			String correspondingTestPath = null;
			for(String testPath : testFiles) {
				String testFilename = new File(testPath).getName();
				if(testFilename.replace("Test.java", ".java").equals(productFilename)) {
					correspondingTestPath = testPath;
				}
			}
			hasTestIndex.put(productPath, correspondingTestPath);
		}
		
		return hasTestIndex;
	}

	public static List<String> listUntestedMethods(String classPath, Map<String, String> hasTestIndex) throws Exception {
		String testPath = hasTestIndex.get(classPath);
		List<String> methodsInProduct = SourceHelpers.listMethodNamesInSrcStr(FileHelpers.readEntireFile(new File(classPath)));
		
		if(testPath != null) {
			List<String> methodsInTest = SourceHelpers.listMethodNamesInSrcStr(FileHelpers.readEntireFile(new File(testPath)));
			List<String> toRemove = new LinkedList<String>();
			for(String testMethod : methodsInTest) {
				String candidate = StringHelpers.capitalizeFirstLetter(testMethod.replace("test", ""));
				if(methodsInProduct.contains(candidate)) {
					toRemove.add(candidate);
				}
			}
			methodsInProduct.removeAll(toRemove);
		}
		
		return methodsInProduct; 
	}

	public static float determineCoverage(String rootPath) throws Exception {
		List<String> allMethods = determineAllMethods(rootPath);
		List<String> untestedMethods = determineUntestedMethods(rootPath);
		
		int numMethods = allMethods.size();
		return (float)(numMethods - untestedMethods.size()) / numMethods;
	}
	
	public static List<String> determineUntestedMethods(String rootPath) throws Exception {
		List<String> untestedMethods = new LinkedList<String>();
		Map<String, String> classHasTest = buildClassHasTestIndex(rootPath);
		List<String> classPaths = FileHelpers.listProductFiles(rootPath);
		for(String classPath : classPaths) {
			untestedMethods.addAll(listUntestedMethods(classPath, classHasTest));
		}
		return untestedMethods;
	}
	
	public static List<String> determineAllMethods(String rootPath) throws Exception {
		List<String> allMethods = new LinkedList<String>();
		List<String> classPaths = FileHelpers.listProductFiles(rootPath);
		for(String classPath : classPaths) {
			String srcStr = FileHelpers.readEntireFile(new File(classPath));
			allMethods.addAll(SourceHelpers.listMethodNamesInSrcStr(srcStr));
		}
		return allMethods;
	}

}
