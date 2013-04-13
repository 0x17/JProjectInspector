package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import java.util.HashMap;
import java.util.Map;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.FunctionIndexer;

public class IndexerRegistry {
	
	public final static Map<String, FunctionIndexer> indexerForExtension = new HashMap<String, FunctionIndexer>();
	
	static {
		register("js", new JavaScriptIndexer());
		register("java", new JavaIndexer());
		register("py", new PythonIndexer());
		register("rb", new RubyIndexer());
	}

	public static void register(String ext, FunctionIndexer indexer) {
		indexerForExtension.put(ext, indexer);
	}

}
