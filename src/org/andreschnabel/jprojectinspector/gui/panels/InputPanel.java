package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.constraints.ThreeRowGridBagConstraints;
import org.andreschnabel.jprojectinspector.gui.windows.CsvTableWindow;
import org.andreschnabel.jprojectinspector.gui.windows.MetricsSelectionWindow;
import org.andreschnabel.jprojectinspector.gui.windows.SettingsWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.TimelineTapper;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.threading.ContinuousTask;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class InputPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ContinuousTask<java.util.List<Project>> tapTimelineTask;

	public InputPanel() {
		setLayout(new GridBagLayout());

		initTopPane();
		initMiddlePane();
		initBottomPane();

		metricsSelectionWindow = new MetricsSelectionWindow(projLstPanel);
	}

	private InputProjectTablePanel projLstPanel;

	private JLabel ownerLbl, repoLbl;

	private final static int NCOLUMNS = 10;

	private final JTextField ownerField = new JTextField(NCOLUMNS);
	private final JTextField repoField = new JTextField(NCOLUMNS);

	private DocumentListener docListener = new EmptyReposComboOnChange();

	private SettingsWindow settingsWindow = new SettingsWindow();
	private final MetricsSelectionWindow metricsSelectionWindow;

	public void dispose() {
		if(tapTimelineTask != null) {
			tapTimelineTask.dipose();
		}
		projLstPanel.dipose();
	}

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
	
	@SuppressWarnings("rawtypes")
	private final JComboBox userReposCombo = new JComboBox();

	private JButton addAllBtn;

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

		add(topPane, ThreeRowGridBagConstraints.topPaneConstraints());
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
				AsyncTask<java.util.List<Project>> scrapeProjectsTask = new AsyncTask<java.util.List<Project>>() {
					@Override
					public java.util.List<Project> doInBackground() {
						java.util.List<Project> scrapedProjs = null;
						if(ProjectDownloader.isUserOnline(owner)) {
							try {
								scrapedProjs = UserScraper.scrapeProjectsOfUser(owner);
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
						return scrapedProjs;
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onFinished(java.util.List<Project> scrapedProjs) {
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
		addAllBtn = new JButton("+All");
		addAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String owner = ownerField.getText();
				java.util.List<Project> projs = new LinkedList<Project>();
				for(int i=0; i<userReposCombo.getItemCount(); i++) {
					String repo = (String)userReposCombo.getItemAt(i);
					projs.add(new Project(owner, repo));
				}
				projLstPanel.addProjects(projs);
			}
		});
		addAllBtn.setVisible(false);
		topPane.add(addAllBtn);
	}

	private void initMiddlePane() {
		projLstPanel = new InputProjectTablePanel();
		add(projLstPanel, ThreeRowGridBagConstraints.middlePaneConstraints());
	}

	private void initBottomPane() {
		JButton viewCsvBtn = new JButton("View CSV");
		viewCsvBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CsvData csvData = GuiHelpers.loadCsvDialog(new File("."));
					if(csvData != null) {
						CsvTableWindow ctw = new CsvTableWindow(csvData);
						ctw.setVisible(true);
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});

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
				if(!projLstPanel.getProjects().isEmpty()) {
					metricsSelectionWindow.setVisible(true);
				} else {
					GuiHelpers.showError("Can't start. Project list is empty.");
				}
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

		JButton importBtn = new JButton("Import");
		importBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CsvData data = GuiHelpers.loadCsvDialog(new File("."));
					if(data != null) {
						for(Project p : Project.projectListFromCsv(data)) {
							projLstPanel.addProject(p);
						}
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GuiHelpers.saveCsvDialog(new File("."), Project.projectListToCsv(projLstPanel.getProjects()));
				} catch(Exception ee) {
					ee.printStackTrace();
				}
			}
		});

		final JButton tapTimelineBtn = new JButton("Tap Timeline");
		tapTimelineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tapTimelineTask == null) {
					tapTimelineTask = new ContinuousTask<List<Project>>() {
						@Override
						public void onSuccess(List<Project> result) {
							for(Project p : result) {
								projLstPanel.addProject(p);
							}
						}

						@Override
						public List<Project> iterateInBackground() {
							try {
								return TimelineTapper.tapProjects();
							} catch(Exception e1) {
								e1.printStackTrace();
							}
							return null;
						}
					};
					tapTimelineTask.execute();
					tapTimelineBtn.setText("Stop Tap Timeline");
				} else {
					tapTimelineTask.dipose();
					tapTimelineTask = null;
					tapTimelineBtn.setText("Tap Timeline");
				}
			}
		});

		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				projLstPanel.clear();
			}
		});

		bottomPane.add(viewCsvBtn);
		bottomPane.add(importBtn);
		bottomPane.add(exportBtn);
		bottomPane.add(tapTimelineBtn);
		bottomPane.add(clearBtn);
		bottomPane.add(configBtn);
		bottomPane.add(remOfflineBtn);
		bottomPane.add(startBtn);

		add(bottomPane, ThreeRowGridBagConstraints.bottomPaneConstraints());
	}


}
