package org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.IFunctionIndexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Registrierung von Quellcode-Indexern für Annäherung der Testabdeckung.
 */
public class IndexerRegistry {
	
	public final static Map<String, IFunctionIndexer> indexerForExtension = new HashMap<String, IFunctionIndexer>();
	
	static {
		register("js", new JavaScriptIndexer());
		register("java", new JavaIndexer());
		register("py", new PythonIndexer());
		register("rb", new RubyIndexer());
	}

	/**
	 * Registriere neuen Quellcode-Indexer für gegebene Dateiendung.
	 * @param ext Dateiendung für welche der gegebene Indexer benutzt werden soll.
	 * @param indexer Quellcode Indexer für gegbene Endung.
	 */
	public static void register(String ext, IFunctionIndexer indexer) {
		indexerForExtension.put(ext, indexer);
	}

}
