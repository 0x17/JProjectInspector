package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.IFunctionIndexer;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.RegexHelpers;

import java.util.List;

/**
 * Quellcode-Indexer für Ruby.
 */
public class RubyIndexer implements IFunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = Func.remDups(RegexHelpers.batchMatchOneGroup("def ([A-Za-z][A-Za-z0-9\\-\\_]*)", src));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcIdentifiers;
	}

	@Override
	public List<String> listFunctionCalls(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = Func.remDups(RegexHelpers.batchMatchOneGroup("([A-Za-z][A-Za-z0-9\\-\\_]*)\\(", src));
			IPredicate<String> notNew = new IPredicate<String>() {
				@Override
				public boolean invoke(String s) {
					return !s.equals("new");
				}
			};
			funcIdentifiers = Func.filter(notNew, funcIdentifiers);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcIdentifiers;
	}
}
