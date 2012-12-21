package org.andreschnabel.jprojectinspector.parsers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.model.code.Clazz;
import org.andreschnabel.jprojectinspector.model.code.Method;
import org.andreschnabel.jprojectinspector.utilities.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class SimpleParser {	
	public CommonTree getParseTreeForSourceFile(File f) throws Exception {
		return getParseTreeForSourceStr(FileHelpers.readEntireFile(f));
	}
	
	public CommonTree getParseTreeForSourceStr(String sourceStr) throws Exception {
		ANTLRStringStream ss = new ANTLRStringStream(sourceStr);
		JavaLexer lexer = new JavaLexer(ss);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		JavaParser parser = new JavaParser(tokens);

		// check for correctness
		parser.compilationUnit();
		parser.reset();
		
		CommonTree t = (CommonTree) parser.javaSource().getTree();
		return t;
	}
	
	public Clazz[] getClassesInSourceFile(File f) throws Exception {
		return getClassesInSourceStr(FileHelpers.readEntireFile(f));
	}
	
	public Clazz[] getClassesInSourceStr(String sourceStr) throws Exception {
		CommonTree tree = getParseTreeForSourceStr(sourceStr);
		List<Clazz> classes = new LinkedList<Clazz>();
		
		List<Tree> classRoots = new LinkedList<Tree>();
		addClassRootsInSourceTree(tree, classRoots);
		
		for(Tree classRoot : classRoots) {
			classes.add(extractClassFromTree(classRoot));
		}
		
		return (Clazz[])classes.toArray();
	}

	private Clazz extractClassFromTree(Tree classRoot) {
		Clazz c = new Clazz();
		
		c.name = classRoot.getChild(1).getText();
		
		List<Method> methods = new LinkedList<Method>();
		Tree classScope = classRoot.getChild(2);
		
		for(int i=0; i<classScope.getChildCount(); i++) {
			Tree t = classScope.getChild(i);
			if(t.getText().endsWith("METHOD_DECL")) {
				Method m = extractMethodFromTree(t);
				m.clazz = c;
				methods.add(m);
			}
		}
		
		return c;
	}

	private Method extractMethodFromTree(Tree methodRoot) {
		Method m = new Method();
		
		m.name = methodRoot.getChild(2).getText();
		
		//Tree blockScope = methodRoot.getChild(3);
		// TODO: Finish
		
		return m;
	}

	private void addClassRootsInSourceTree(Tree tree, List<Tree> classRoots) {
		if(tree.getText().equals("class")) {
			classRoots.add(tree);
		}
		
		for(int i=0; i<tree.getChildCount(); i++) {
			addClassRootsInSourceTree(tree.getChild(i), classRoots);
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleParser sp = new SimpleParser();
		CommonTree tree = sp.getParseTreeForSourceFile(new File(TestCommon.TEST_SRC_FILENAME));
		System.out.println(tree.toStringTree());
	}
	
}
