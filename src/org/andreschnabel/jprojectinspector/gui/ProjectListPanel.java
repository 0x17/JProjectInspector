package org.andreschnabel.jprojectinspector.gui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;


public class ProjectListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JList<String> jplist;
	private List<Project> projects = new ArrayList<Project>();

	public ProjectListPanel() {
		setLayout(new FlowLayout());
		
		jplist = new JList<String>(new String[]{});
		add(jplist);
	}
	
	public void addProject(Project p) {
		ListHelpers.addNoDups(projects, p);		
		updateLst();
	}
	
	public void updateLst() {
		String[] projsArray = new String[projects.size()];
		for(int i=0; i<projsArray.length; i++) {
			projsArray[i] = projects.get(i).toString();
		}
		
		jplist.setListData(projsArray);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void removeOffline() {
		Predicate<Project> isOnline = new Predicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				return ProjectDownloader.isProjectOnline(p);
			}
		};
		projects = ListHelpers.filter(isOnline, projects);
		updateLst();
	}

}
