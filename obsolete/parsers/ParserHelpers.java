package org.andreschnabel.jprojectinspector.parsers;

import java.io.File;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;

public class ParserHelpers {
	
	private ParserHelpers() {}

	public static JavaParser parserForFile(File f) throws Exception {
		return parserForStr(FileHelpers.readEntireFile(f));
	}

	public static JavaParser parserForStr(String s) throws Exception {
		ANTLRStringStream ss = new ANTLRStringStream(s);
		JavaLexer lexer = new JavaLexer(ss);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		parser.compilationUnit();
		parser.reset();
		return parser;
	}

}
