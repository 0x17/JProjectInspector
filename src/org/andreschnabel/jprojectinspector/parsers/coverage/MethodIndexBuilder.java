package org.andreschnabel.jprojectinspector.parsers.coverage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.coverage.Method;
import org.andreschnabel.jprojectinspector.model.coverage.MethodIndex;
import org.andreschnabel.jprojectinspector.parsers.JavaParser;
import org.andreschnabel.jprojectinspector.parsers.ParserHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class MethodIndexBuilder {
	
	public MethodIndex buildIndexForProject(File root) throws Exception {
		MethodIndex index = new MethodIndex();
		
		List<File> filesToParse = collectNonTestFiles(root);
		for(File f : filesToParse)
			index.methods.addAll(parseFile(f));
		
		return index;
	}

	public List<Method> parseFile(File f) throws Exception {
		String packName = determinePackageNameForFile(f);
		JavaParser parser = ParserHelpers.parserForFile(f);		
		return extractMethods(parser, packName);
	}
	
	public List<Method> parseSrcStr(String s) throws Exception {
		String packName = determinePackageNameForSrcStr(s);
		JavaParser parser = ParserHelpers.parserForStr(s);
		return extractMethods(parser, packName);
	}
	
	public static List<Method> extractMethods(JavaParser jp, String packageName) throws Exception {
		CommonTree t = (CommonTree)jp.javaSource().getTree(); 
		List<Method> methods = new LinkedList<Method>();
		extractMethods(t, methods, packageName, null);
		return methods;
	}

	public static String determinePackageNameForFile(File f) throws Exception {
		String srcStr = FileHelpers.readEntireFile(f);		
		return determinePackageNameForSrcStr(srcStr);
	}
	
	public static String determinePackageNameForSrcStr(String srcStr) throws Exception {
		Pattern p = Pattern.compile("package\\s+([\\w\\.]+);");
		Matcher m = p.matcher(srcStr);
		if(m.find()) {
			return m.group(1);
		} else throw new Exception("Unable to match package declaration!");
	}

	private static String[] extractParams(Tree paramLst) {
		int numParams = paramLst.getChildCount();
		String[] params = new String[numParams];
		for(int i=0; i<numParams; i++) {
			Tree node = paramLst.getChild(i).getChild(1).getChild(0);
			params[i] = node.getText();
			// Array
			if(params[i].equals("QUALIFIED_TYPE_IDENT") && node.getType() == 138) {
				params[i] = node.getChild(0).getText() + "[]";
			}
		}
		return params;
	}

	private static void extractMethods(Tree t, List<Method> methods, String packageName, String className) {
		int type = t.getType();
		switch(type) {
			case 35: // class
				extractMethods(t.getChild(2), methods, packageName, ((className != null) ? className + "." : "") + t.getChild(1).getText());
				break;
			case 43: // constructor decl
				methods.add(new Method(null, packageName, className, className, extractParams(t.getChild(1))));
				break;
			case 79: // function method decl
				{
					String retType = t.getChild(1).getChild(0).getText();
					String identifier = t.getChild(2).getText();
					String[] params = extractParams(t.getChild(3));
					methods.add(new Method(retType, packageName, className, identifier, params));
				}
				break;
			case 177: // void method decl
				{
					String retType = "void";
					String identifier = t.getChild(1).getText();
					String[] params = extractParams(t.getChild(2));
					methods.add(new Method(retType, packageName, className, identifier, params));
				}
				break;
			default:
				for(int i=0; i<t.getChildCount(); i++) {
					extractMethods(t.getChild(i), methods, packageName, className);
				}
		}
	}

	private List<File> collectNonTestFiles(File root) throws Exception {
		List<File> nonTestFiles = new LinkedList<File>();
		traverseRecursively(root, nonTestFiles);
		return nonTestFiles;
	}

	private void traverseRecursively(File root, List<File> nonTestFiles) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles()) {
				traverseRecursively(f, nonTestFiles);
			}
		}
		else {
			String filename = root.getName();
			if(filename.endsWith(".java") && !UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(root), filename)) {
				nonTestFiles.add(root);
			}
		}
	}
}
