package org.andreschnabel.jprojectinspector.metrics.test.coverage;

import java.util.List;

public interface FunctionIndexer {

	public List<String> listFunctionDeclarations(String src);
	public List<String> listFunctionCalls(String src);

}
