package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;

import java.util.LinkedList;
import java.util.List;

public class JavaScriptIndexer implements FunctionIndexer {
	@Override
	public List<String> listFunctionDeclarations(String src) {
		return new LinkedList<String>();
	}

	@Override
	public List<String> listFunctionCalls(String src) {
		return new LinkedList<String>();
	}
}
