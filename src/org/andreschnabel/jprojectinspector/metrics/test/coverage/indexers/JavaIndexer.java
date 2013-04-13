package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaIndexer implements FunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcNames = new LinkedList<String>();
		String methodRegex = "(private|public|protected)?(\\s+static)?\\s+\\w+\\s+(\\w+)\\(.*\\)";
		Pattern p = Pattern.compile(methodRegex);
		Matcher m = p.matcher(src);
		while(m.find()) {
			if(m.groupCount() == 3) {
				String funcName = m.group(3);
				ListHelpers.addNoDups(funcNames, funcName);
			}
		}
		return funcNames;
	}

	@Override
	public List<String> listFunctionCalls(String src) {
		List<String> calledMethods = null;
		try {
			calledMethods = ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("\\w+\\.(\\w+)\\(.*\\)", src));
			calledMethods.addAll(ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("new ([^<]+?)\\(", src)));
			calledMethods.addAll(ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("new (.+?)<.+?>\\(", src)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return calledMethods;
	}
}
