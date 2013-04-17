package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.tables.InputProjectTableModel;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTaskBatch;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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
				Predicate<Project> isOffline = new Predicate<Project>() {
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
}
