package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.model.UserData;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.pecker.helpers.GuiHelpers;
import org.andreschnabel.pecker.threading.AsyncTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Panel zur Anzeige von Statistiken für einen bestmimten User.
 * Zeigt Statistiken in einer Art Gitter an.
 *
 * Attribut: Ausprägung.
 */
public class UserStatsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final Map<String, String> stats = new TreeMap<String, String>();
	private final String user;
	private List<String> statNames = new LinkedList<String>();

	/**
	 * Konstruktor. Erzeugt Panel mit Statistiken eines gegebenen GitHub-Nutzers.
	 * @param user login name eines GitHub-Nutzers.
	 */
	public UserStatsPanel(final String user) {
		this.user = user;
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
		setLayout(new GridLayout(7, 2));
		for(String statName : statNames) {
			String val = stats.get(statName);
			add(new JLabel(statName+":"));
			add(new JLabel(val));
		}
		add(new JLabel(""));
		JButton openInBrowserBtn = new JButton("Open in browser");
		openInBrowserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GuiHelpers.openUrl("https://github.com/" + user);
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(openInBrowserBtn);

		updateUI();
	}

	private void addStat(String statName, String value) {
		statNames.add(statName);
		stats.put(statName, value);
	}
}
