package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.tables.InputProjectTableModel;
import org.andreschnabel.jprojectinspector.gui.windows.UserStatsWindow;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.FuncInPlace;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.threading.AsyncTask;
import org.andreschnabel.pecker.threading.AsyncTaskBatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabellenpanel für gewählte Projekte aus dem Eingabedialog.
 *
 * Je Projekt eine Zeile.
 * Spalten sind owner, repo, #branches, #commits, #forks, #issues, #pullreqs, #stars.
 *
 */
public class InputProjectTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Project> projects = new ArrayList<Project>();
	private final InputProjectTableModel tableModel = new InputProjectTableModel(projects);
	private final JTable projTable = new JTable(tableModel);
	private AsyncTaskBatch<FrontStats> queryStatsTaskBatch;

	public InputProjectTablePanel() {
		setLayout(new GridLayout(1,1));
		JScrollPane scrollPane = new JScrollPane(projTable);
		add(scrollPane);
		projTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int selRowIndex = projTable.getSelectedRow();
				int selColumnIndex = projTable.getSelectedColumn();
				if(selColumnIndex == 0) {
					String user = (String) projTable.getValueAt(selRowIndex, selColumnIndex);
					new UserStatsWindow(user).setVisible(true);
				}
			}
		});
	}
	
	public void addProject(final Project p) {
		if(FuncInPlace.addNoDups(projects, p)) {
			AsyncTask<FrontStats> queryStatsTask = queryTaskForProject(p);
			updateTable();
			queryStatsTask.execute();
		}
	}

	private AsyncTask<FrontStats> queryTaskForProject(final Project p) {
		return new AsyncTask<FrontStats>() {
				@Override
				public void onFinished(FrontStats stats) {
					if(stats != null) {
						tableModel.putInCache(p, stats);
						updateTable();
					}
				}
				@Override
				public FrontStats doInBackground() {
					FrontStats stats = null;
					try {
						stats = FrontStats.statsForProject(p);
					} catch(Exception e) {
						e.printStackTrace();
					}
					return stats;
				}
			};
	}

	public void addProjects(List<Project> projs) {
		if(queryStatsTaskBatch == null || queryStatsTaskBatch.isDone()) {
			queryStatsTaskBatch = new AsyncTaskBatch<FrontStats>(projs.size());
			int nadded = 0;
			for(Project p : projs) {
				if(FuncInPlace.addNoDups(projects, p)) {
					queryStatsTaskBatch.add(queryTaskForProject(p));
					nadded++;
				}
			}
			if(nadded > 0) {
				updateTable();
				queryStatsTaskBatch.execute();
			}
		}
	}

	public void removeOffline() {
		AsyncTask<List<Project>> determineOfflineProjsTask = new AsyncTask<List<Project>>() {
			@Override
			public void onFinished(List<Project> toRem) {
				projects.removeAll(toRem);
				updateTable();
			}
			@Override
			public List<Project> doInBackground() {
				IPredicate<Project> isOffline = new IPredicate<Project>() {
					@Override
					public boolean invoke(Project p) {
						return !ProjectDownloader.isProjectOnline(p);
					}
				};
				List<Project> toRem = Func.filter(isOffline, projects);
				return toRem;
			}
		};
		determineOfflineProjsTask.execute();
	}

	public synchronized void updateTable() {
		projTable.updateUI();
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void clear() {
		projects.clear();
		projTable.updateUI();
	}

	public void dipose() {
		if(queryStatsTaskBatch != null) {
			queryStatsTaskBatch.dipose();
			queryStatsTaskBatch = null;
		}
	}

	public void removeSelectedRow() {
		int selRowIndex = projTable.getSelectedRow();
		if(selRowIndex != -1) {
			projects.remove(selRowIndex);
			projTable.updateUI();
		}
	}
}
