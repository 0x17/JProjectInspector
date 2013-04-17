package org.andreschnabel.jprojectinspector.parsers.textmining;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.parsers.JavaLexer;
import org.andreschnabel.jprojectinspector.parsers.LexerHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.antlr.runtime.Token;

public class FeatureExtractor {
	
	private FeatureExtractor() {}
	
	public static Map<String, Integer> extractTokenCounts(String srcStr) throws Exception {
		Map<String, Integer> tokenCounts = new HashMap<String, Integer>();
		JavaLexer lexer = LexerHelpers.lexerForStr(srcStr);
		Token token;
		while ((token = lexer.nextToken()).getType() != Token.EOF) {
			String tokenText = token.getText();
			tokenCounts.put(tokenText, tokenCounts.containsKey(tokenText) ? tokenCounts.get(tokenText) + 1 : 1);
		}
		return tokenCounts;
	}
	
	public static void printTokenCountsDesc(final Map<String, Integer> tokenCounts) {
		List<String> keys = new LinkedList<String>(tokenCounts.keySet());
		Collections.sort(keys, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				int acnt = tokenCounts.get(a);
				int bcnt = tokenCounts.get(b);
				return acnt == bcnt ? 0 : (acnt < bcnt ? 1 : -1);
			}
			
		});
		for(String token : keys) {
			System.out.println("num(" + token + ")=" + tokenCounts.get(token));
		}
	}
	
	public static void writeTokenCountsToArffFile(final Map<String, Integer> tokenCounts, String outFilename) throws Exception {
		StringBuilder sb = new StringBuilder();
		// header
		sb.append("@relation tokencounts\n");
		sb.append("@attribute token string\n");
		sb.append("@attribute count integer\n");
		// data
		sb.append("@data\n");
		for(String token : tokenCounts.keySet()) {
			sb.append("'" + token + "'," + tokenCounts.get(token) + "\n");
		}
		FileHelpers.writeStrToFile(sb.toString(), outFilename);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Integer> result = extractTokenCounts(FileHelpers.readEntireFile(new File("testdata/Points.java")));
		printTokenCountsDesc(result);
		writeTokenCountsToArffFile(result, "test.arff");
	}

}
