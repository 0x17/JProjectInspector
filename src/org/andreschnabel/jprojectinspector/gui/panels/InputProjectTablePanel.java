package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.tables.InputProjectTableModel;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class InputProjectTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Project> projects = new ArrayList<Project>();
	private final InputProjectTableModel tableModel = new InputProjectTableModel(projects);
	private final JTable projTable = new JTable(tableModel);

	public InputProjectTablePanel() {
		setLayout(new GridLayout(1,1));
		JScrollPane scrollPane = new JScrollPane(projTable);
		add(scrollPane);
	}
	
	public void addProject(final Project p) {
		AsyncTask<FrontStats> queryStatsTask = new AsyncTask<FrontStats>() {
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
		FuncInPlace.addNoDups(projects, p);
		updateTable();
		queryStatsTask.execute();
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
}
