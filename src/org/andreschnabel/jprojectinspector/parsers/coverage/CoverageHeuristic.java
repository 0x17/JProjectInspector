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
		// TODO Auto-generated method stub
		
		// List methods of class file (Class.java) using SourceHelpers
		// If there's no ClassTest.java for this Class then return all methods
		// Else
		// 	List methods of test (ClassTest.java) using SourceHelpers
		//		look for each method if there's a testMethod in the ClassTest.java
		//			if there's no corresponding test method add it to the result
		
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

	public static float determineCoverage(String rootPath) {
		// TODO Auto-generated method stub
		
		// Recursively traverse sub-directory tree below rootPath.
		// Basically build list of ALL methods in path by adding all methods of each non-test class
		// Then build list of ALL untested methods in path by adding all untested methods of each non-test class
		// Coverage = (float)(#all-methods - #untested-methods) / #all-methods
		
		return 0;
	}

}
