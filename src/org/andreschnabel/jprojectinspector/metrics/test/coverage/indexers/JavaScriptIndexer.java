package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

public class JavaScriptIndexer implements FunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = RegexHelpers.batchMatchOneGroup("function (\\w+)\\(", src);
			funcIdentifiers.addAll(RegexHelpers.batchMatchOneGroup("(\\w+) = function\\(\\)", src));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ListHelpers.remDups(funcIdentifiers);
	}

	@Override
	public List<String> listFunctionCalls(final String src) {
		final List<String> declaredFuncs = listFunctionDeclarations(src);
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = RegexHelpers.batchMatchOneGroup("([A-Za-z]\\w*)[\\(\\)]", src);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Predicate<String> notDeclaredAndNotConstructor = new Predicate<String>() {
			@Override
			public boolean invoke(String s) {
				return !declaredFuncs.contains(s) && !src.contains("new " + s);
			}
		};
		return ListHelpers.remDups(ListHelpers.filter(notDeclaredAndNotConstructor, funcIdentifiers));
	}
}
