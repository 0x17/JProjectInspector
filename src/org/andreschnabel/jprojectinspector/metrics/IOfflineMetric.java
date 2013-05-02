package org.andreschnabel.jprojectinspector.metrics;

import java.io.File;

/**
 * Offline-Metrik.<br />
 * Misst für Pfad zu geklontem Repository eine Eigenschaft auf diesem<br />
 * und gibt das Ergebnis als Fließpunktzahl mit doppelter Genauigkeit wieder<br />
 * zurück.
 */
public interface IOfflineMetric {

	/**
	 * Getter für Namen.
	 * @return Namen der Metrik.
	 */
	public String getName();

	/**
	 * Getter für Beschreibung.
	 * @return Beschreibung des Vorgehens der Metrik.
	 */
	public String getDescription();

	/**
	 * Messfunktion
	 * @param repoRoot Pfad zu geklontem Repository
	 * @return Messergebnis
	 * @throws Exception
	 */
	public double measure(File repoRoot) throws Exception;

}
