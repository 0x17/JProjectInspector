package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabellenmodell für die Auswahl von Projekten zur Messung.
 * Zeilen sind Projekte.
 * Spalten sind owner, repo, #branches, #commits, #forks, #issues, #pullreqs, #stars.
 */
public class InputProjectTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<Project, FrontStats> statsCache = new HashMap<Project, FrontStats>();
	private final List<Project> projects;

	public void putInCache(Project p, FrontStats stats) {
		statsCache.put(p, stats);
	}

	public InputProjectTableModel(final List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Owner";
			case 1: return "Repo";
			case 2: return "#Branches";
			case 3: return "#Commits";
			case 4: return "#Forks";
			case 5: return "#Issues";
			case 6: return "#PullReqs";
			case 7: return "#Stars";
		}
		return "";
	}

	@Override
	public int getRowCount() {
		return projects.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Project p = projects.get(rowIndex);
		FrontStats stats;
		if(statsCache.containsKey(p)) {
			stats = statsCache.get(p);
		} else {
			stats = new FrontStats();
		}

		switch(columnIndex) {
			case 0:
				return p.owner;
			case 1:
				return p.repoName;
			case 2:
				return stats.nbranches;
			case 3:
				return stats.ncommits;
			case 4:
				return stats.nforks;
			case 5:
				return stats.nissues;
			case 6:
				return stats.npullreqs;
			case 7:
				return stats.nstars;
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
