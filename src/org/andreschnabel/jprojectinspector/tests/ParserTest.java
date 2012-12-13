package org.andreschnabel.jprojectinspector.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.andreschnabel.jprojectinspector.parsers.JavaLexer;
import org.andreschnabel.jprojectinspector.parsers.JavaParser;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.antlr.stringtemplate.StringTemplate;

public class ParserTest {
	@SuppressWarnings("unused")
	private static void printTokens(CommonTokenStream tokens) {
		tokens.fill();
		@SuppressWarnings("unchecked")
		List<Token> myTokens = tokens.getTokens();
		System.out.println("Tokens:");
		for (Token t : myTokens) {
			System.out.println("Token = " + t.toString() + " (" + t.getType() + ")");
		}
		System.out.println("End Of Tokens");
		tokens.reset();
	}

	public static void printTree(Tree t) {
		System.out.println(t.toStringTree());
	}

	public static void main(String[] args) throws Exception {
		ANTLRFileStream input = new ANTLRFileStream(args[0]);
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// printTokens(tokens);

		JavaParser parser = new JavaParser(tokens);
		
		// Check for correctness
		parser.compilationUnit();
		parser.reset();
		
		CommonTree t = (CommonTree) parser.javaSource().getTree();

		// printTree(t);

		traverse(t);

		//saveTreeToDot(t, args[0]);
	}

	@SuppressWarnings("unused")
	private static void saveTreeToDot(Tree t, String srcName) throws IOException {
		DOTTreeGenerator treeGen = new DOTTreeGenerator();
		StringTemplate out = treeGen.toDOT(t);
		FileWriter fw = new FileWriter(srcName.replaceAll(".java", "") + ".dot");
		fw.write(out.toString());
		fw.close();
	}

	private static void traverse(Tree root) {
		traverse(root, 0);
	}

	private static void traverse(Tree root, int layer) {
		for (int i = 0; i < root.getChildCount(); i++) {
			Tree child = root.getChild(i);
			// System.out.println(child + " layer=" + layer);
			if(child.getType() == 29) {
				System.out.println(child.getChild(0).getChild(0).getText());
			}

			traverse(child, layer + 1);
		}
	}
}