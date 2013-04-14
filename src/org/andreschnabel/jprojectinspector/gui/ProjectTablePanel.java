package org.andreschnabel.jprojectinspector.gui;

import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Project> projects = new ArrayList<Project>();
	private final ProjectTableModel tableModel = new ProjectTableModel(projects);
	private final JTable projTable = new JTable(tableModel);

	public ProjectTablePanel() {
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
		ListHelpers.addNoDups(projects, p);
		queryStatsTask.execute();
	}
	
	public void updateTable() {
		projTable.updateUI();
	}

	public List<Project> getProjects() {
		return projects;
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
				List<Project> toRem = ListHelpers.filter(isOffline, projects);
				return toRem;
			}
		};
		determineOfflineProjsTask.execute();
	}

}
