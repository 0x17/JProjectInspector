package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

public class RubyIndexer implements FunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("def ([A-Za-z][A-Za-z0-9\\-\\_]*)", src));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcIdentifiers;
	}

	@Override
	public List<String> listFunctionCalls(String src) {
		List<String> funcIdentifiers = null;
		try {
			funcIdentifiers = ListHelpers.remDups(RegexHelpers.batchMatchOneGroup("([A-Za-z][A-Za-z0-9\\-\\_]*)\\(", src));
			Predicate<String> notNew = new Predicate<String>() {
				@Override
				public boolean invoke(String s) {
					return !s.equals("new");
				}
			};
			funcIdentifiers = ListHelpers.filter(notNew, funcIdentifiers);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcIdentifiers;
	}
}