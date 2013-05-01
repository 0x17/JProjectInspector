package org.andreschnabel.jprojectinspector.metrics.test.coverage;

import java.util.List;

/**
 * Schnittstelle zum Indizieren von Funktionen in Quelltext.
 */
public interface IFunctionIndexer {

	/**
	 * Liste die deklarierten Funktionenbezeichner in gegebenem Quelltext auf.
	 * @param src Quelltext.
	 * @return Liste der deklarierten Funktionenbezeichner.
	 */
	public List<String> listFunctionDeclarations(String src);

	/**
	 * Liste die Funktionenaufrufe in gegebenem Quelltext auf.
	 * @param src Quelltext.
	 * @return Liste der aufgerufenen Funktionenenbezeichner.
	 */
	public List<String> listFunctionCalls(String src);

}
