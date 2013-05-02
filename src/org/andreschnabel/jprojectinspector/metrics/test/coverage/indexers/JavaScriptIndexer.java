package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.IFunctionIndexer;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

/**
 * Quellcode Indexer für JavaScript.
 */
public class JavaScriptIndexer implements IFunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = RegexHelpers.batchMatchOneGroup("function (\\w+)\\(", src);
			funcIdentifiers.addAll(RegexHelpers.batchMatchOneGroup("(\\w+) = function\\(\\)", src));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Func.remDups(funcIdentifiers);
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
		IPredicate<String> notDeclaredAndNotConstructor = new IPredicate<String>() {
			@Override
			public boolean invoke(String s) {
				return !declaredFuncs.contains(s) && !src.contains("new " + s);
			}
		};
		return Func.remDups(Func.filter(notDeclaredAndNotConstructor, funcIdentifiers));
	}
}
