package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

/**
 * Online-Metrik. Basierend auf Scraping oder GitHubAPI (Kategorie).<br />
 * Misst für ein Projekt identifiziert durch (owner,repo)-Tupel eine bestimmte<br />
 * Eigenschaft per Scraping oder Web-API und liefert das Ergebnis als<br />
 * Fließpunktzahl mit doppelter Genauigkeit zurück.
 */
public interface IOnlineMetric {

	/**
	 * Mögliche Kategorien von Online-Metriken:<br />
	 * Scraping ermittelt Daten von GitHub-Webseite durch reguläre Ausdrücke oder XPath,<br />
	 * GitHubApi ermittelt Daten von GitHub-WebAPI durch Parsen von JSON.
	 */
	public static enum Category {
		Scraping,
		GitHubApi
	}

	/**
	 * Getter für Namen.
	 * @return Namen der Metrik.
	 */
	public String getName();

	/**
	 * Getter für Beschreibung.
	 * @return Beschreibung der Vorgehensweise der Metrik.
	 */
	public String getDescription();

	/**
	 * Getter für Kategorie.
	 * @return Kategorie der Metrik: Scraping oder Web-API.
	 * @see Category
	 */
	public Category getCategory();

	/**
	 * Messung durchführen.
	 * @param p Projekttupel (owner, repo)
	 * @return Messergebnis.
	 * @throws Exception
	 */
	public double measure(Project p) throws Exception;

}
