package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.model.UserData;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTask;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserStatsPanel extends JPanel {

	private final Map<String, String> stats = new TreeMap<String, String>();
	private List<String> statNames = new LinkedList<String>();

	public UserStatsPanel(final String user) {
		setLayout(new FlowLayout());
		add(new JLabel("Fetching data. Please wait..."));
		AsyncTask<UserData> getStatsTask = new AsyncTask<UserData>() {
			@Override
			public void onFinished(UserData data) {
				if(data == null) {
					return;
				}
				initStatsMap(data);
				addStatsToPanel();
			}

			@Override
			public UserData doInBackground() {
				try {
					return UserScraper.scrapeUser(user);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		getStatsTask.execute();
	}

	private void initStatsMap(UserData data) {
		addStat("Login", data.name);
		addStat("Name", data.realName);
		addStat("Join date", data.joinDate);
		addStat("Follower count", "" + data.followers.size());
		addStat("Following count", ""+data.following.size());
		addStat("Project count", ""+data.projects.size());
	}

	private void addStatsToPanel() {
		removeAll();
		setLayout(new GridLayout(6, 2));
		for(String statName : statNames) {
			String val = stats.get(statName);
			add(new JLabel(statName+":"));
			add(new JLabel(val));
		}
		updateUI();
	}

	private void addStat(String statName, String value) {
		statNames.add(statName);
		stats.put(statName, value);
	}
}
