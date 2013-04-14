package org.andreschnabel.jprojectinspector.gui;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final List<Project> projects = new ArrayList<Project>();
	private final JTable projTable = new JTable(new ProjectTableModel(projects));

	public ProjectTablePanel() {
		JScrollPane scrollPane = new JScrollPane(projTable);
		add(scrollPane);
	}
	
	public void addProject(Project p) {
		ListHelpers.addNoDups(projects, p);		
		updateTable();
	}
	
	public void updateTable() {
		projTable.updateUI();
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void removeOffline() {
		Predicate<Project> isOffline = new Predicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				return !ProjectDownloader.isProjectOnline(p);
			}
		};
		ListHelpers.removeAll(isOffline, projects);
		updateTable();
	}

}
