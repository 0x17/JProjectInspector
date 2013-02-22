package org.andreschnabel.jprojectinspector.parsers;

import org.antlr.runtime.ANTLRStringStream;

public class LexerHelpers {
	
	private LexerHelpers() {}
	
	public static JavaLexer lexerForStr(String s) throws Exception {
		ANTLRStringStream ss = new ANTLRStringStream(s);
		JavaLexer lexer = new JavaLexer(ss);
		return lexer;
	}

}
