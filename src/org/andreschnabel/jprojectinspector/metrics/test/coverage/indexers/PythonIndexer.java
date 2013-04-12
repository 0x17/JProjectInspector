package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

public class PythonIndexer implements FunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = RegexHelpers.batchMatchOneGroup("def ([A-Za-z].+?)\\(", src);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ListHelpers.remDups(funcIdentifiers);
	}

	@Override
	public List<String> listFunctionCalls(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("([A-Za-z][A-Za-z0-9\\-\\_]+?)\\(", src));
			funcIdentifiers.removeAll(listFunctionDeclarations(src));
			funcIdentifiers.removeAll(RegexHelpers.batchMatchOneGroup("class ([A-Za-z].+?)\\(", src));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcIdentifiers;
	}
}
