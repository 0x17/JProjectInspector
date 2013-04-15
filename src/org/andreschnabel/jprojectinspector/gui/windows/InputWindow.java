package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.listeners.QuitOnEscapeKeyListener;
import org.andreschnabel.jprojectinspector.gui.tables.InputProjectTablePanel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.utilities.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InputWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private InputProjectTablePanel projLstPanel;
	
	private JLabel ownerLbl, repoLbl;
	
	private final static int NCOLUMNS = 10;

	private final JTextField ownerField = new JTextField(NCOLUMNS);
	private final JTextField repoField = new JTextField(NCOLUMNS);

	private KeyListener keyListener = new QuitOnEscapeKeyListener();
	private DocumentListener docListener = new EmptyReposComboOnChange();

	private SettingsWindow settingsWindow = new SettingsWindow();
	private final MetricsSelectionWindow metricsSelectionWindow;

	private final class EmptyReposComboOnChange implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			empty();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			empty();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			empty();
		}

		private void empty() {
			userReposCombo.removeAllItems();
			userReposCombo.setVisible(false);
			addAllBtn.setVisible(false);
		}
	}
	private final JComboBox userReposCombo = new JComboBox();

	private JButton addAllBtn = new JButton("+All");

	public InputWindow() {
		super("Inputs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		initTopPane();
		initMiddlePane();
		initBottomPane();
		
		setSize(750, 540);
		setLocationRelativeTo(null);

		metricsSelectionWindow = new MetricsSelectionWindow(projLstPanel);
	}

	private void initTopPane() {
		JPanel topPane = new JPanel();
		topPane.setLayout(new FlowLayout());

		ownerLbl = new JLabel("owner:");
		topPane.add(ownerLbl);
		topPane.add(ownerField);
		ownerField.getDocument().addDocumentListener(docListener);

		repoLbl = new JLabel("repo:");

		topPane.add(repoLbl);
		topPane.add(repoField);

		initAddButton(topPane);
		initQueryProjsButton(topPane);
		initUserReposCombo(topPane);
		initAddAllButton(topPane);

		add(topPane, topPaneConstraints());
		addKeyListenerToPanel(topPane);
	}

	private void initAddButton(JPanel topPane) {
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String owner = ownerField.getText();
				String repo = repoField.getText();
				if(owner != null && !owner.isEmpty() && repo != null && !repo.isEmpty()) {
					projLstPanel.addProject(new Project(owner, repo));
				}
			}
		});
		topPane.add(addBtn);
	}

	private void initQueryProjsButton(JPanel topPane) {
		JButton queryProjsBtn = new JButton("Query");
		queryProjsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				final String owner = ownerField.getText();
				if(owner == null || owner.isEmpty()) return;
				userReposCombo.removeAll();
				AsyncTask<List<Project>> scrapeProjectsTask = new AsyncTask<List<Project>>() {
					@Override
					public List<Project> doInBackground() {
						List<Project> scrapedProjs = null;
						if(ProjectDownloader.isUserOnline(owner)) {
							try {
								scrapedProjs = UserScraper.scrapeProjectsOfUser(owner);
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
						return scrapedProjs;
					}

					@Override
					public void onFinished(List<Project> scrapedProjs) {
						if(scrapedProjs != null) {
							for(Project p : scrapedProjs) {
								userReposCombo.addItem(p.repoName);
							}
							userReposCombo.setVisible(true);
							addAllBtn.setVisible(true);
						}
					}
				};
				scrapeProjectsTask.execute();
			}
		});
		topPane.add(queryProjsBtn);
	}

	private void initUserReposCombo(JPanel topPane) {
		userReposCombo.setEditable(false);
		topPane.add(userReposCombo);
		userReposCombo.setVisible(false);
		userReposCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				String repoName = (String) ie.getItem();
				if(repoName != null && !repoName.isEmpty()) {
					repoField.setText(repoName);
				}
			}
		});
	}

	private void initAddAllButton(JPanel topPane) {
		addAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String owner = ownerField.getText();
				for(int i=0; i<userReposCombo.getItemCount(); i++) {
					String repo = (String)userReposCombo.getItemAt(i);
					projLstPanel.addProject(new Project(owner, repo));
				}
			}
		});
		addAllBtn.setVisible(false);
		topPane.add(addAllBtn);
	}

	private void initMiddlePane() {
		projLstPanel = new InputProjectTablePanel();
		add(projLstPanel, middlePaneConstraints());
	}

	private void initBottomPane() {
		JButton remOfflineBtn = new JButton("Remove offline");
		remOfflineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				projLstPanel.removeOffline();
			}
		});

		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				metricsSelectionWindow.setVisible(true);
			}
		});

		JButton configBtn = new JButton("Config");
		configBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsWindow.setVisible(true);
			}
		});

		JPanel bottomPane = new JPanel(new FlowLayout());

		bottomPane.add(configBtn);
		bottomPane.add(remOfflineBtn);
		bottomPane.add(startBtn);

		add(bottomPane, bottomPaneConstraints());
		addKeyListenerToPanel(bottomPane);
	}

	private void addKeyListenerToPanel(JPanel p) {
		for(Component c : p.getComponents()) {
			c.addKeyListener(keyListener);
		}
	}

	private GridBagConstraints topPaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		return gbc;
	}

	private GridBagConstraints middlePaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = gbc.weighty = 1;
		return gbc;
	}

	private GridBagConstraints bottomPaneConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 2;
		return gbc;
	}

}
