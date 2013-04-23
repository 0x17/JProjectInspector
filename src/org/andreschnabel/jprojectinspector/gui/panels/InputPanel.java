package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.constraints.ThreeRowGridBagConstraints;
import org.andreschnabel.jprojectinspector.gui.windows.CsvTableWindow;
import org.andreschnabel.jprojectinspector.gui.windows.MetricsSelectionWindow;
import org.andreschnabel.jprojectinspector.gui.windows.SettingsWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
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

public class InputPanel extends JPanel implements ILaunchSettings {
	
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

	private final static int NCOLUMNS = 10;

	private final JTextField ownerField = new JTextField(NCOLUMNS);
	private final JTextField repoField = new JTextField(NCOLUMNS);

	private DocumentListener docListener = new EmptyReposComboOnChange();

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
	
	private final JComboBox userReposCombo = new JComboBox();

	private JButton addAllBtn;

	private void initTopPane() {
		JPanel topPane = new JPanel();
		topPane.setLayout(new FlowLayout());

		JLabel ownerLbl = new JLabel("owner:");
		topPane.add(ownerLbl);
		topPane.add(ownerField);
		ownerField.getDocument().addDocumentListener(docListener);

		JLabel repoLbl = new JLabel("repo:");

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
		addBtn.setToolTipText("Adds project with owner and repo from corresponding text fields.");
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
		queryProjsBtn.setToolTipText("Queries all repositories this owner has on GitHub.");
		queryProjsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				final String owner = ownerField.getText();
				if(owner == null || owner.isEmpty()) return;
				userReposCombo.removeAllItems();
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
		JPanel bottomPane = new JPanel(new GridLayout(2, 5));

		JButton viewCsvBtn = new JButton("View CSV");
		viewCsvBtn.setToolTipText("Preview content of CSV file in a visual table.");
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
					GuiHelpers.showError(e1.getMessage());
				}
			}
		});

		JButton remOfflineBtn = new JButton("Remove Offline");
		remOfflineBtn.setToolTipText("Removes projects with \"404\" profile pages.");
		remOfflineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				projLstPanel.removeOffline();
			}
		});

		JButton startBtn = new JButton("Start Measurements");
		startBtn.setToolTipText("Open metrics selection dialog as next step before measuring.");
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

		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setToolTipText("Configure paths containing binaries and used for temporary data.");
		settingsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryShowSettingsWindow();
			}
		});

		JButton importBtn = new JButton("Import from CSV");
		importBtn.setToolTipText("Import project list from CSV file with \"owner,repo\" columns.");
		importBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] expectedHeaders = Project.csvHeaders;
					CsvData data = GuiHelpers.loadCsvDialog(new File("."), expectedHeaders);
					if(data != null) {
						for(Project p : Project.projectListFromCsv(data)) {
							projLstPanel.addProject(p);
						}
					}
				} catch(Exception e1) {
					e1.printStackTrace();
					GuiHelpers.showError(e1.getMessage());
				}
			}
		});

		JButton exportBtn = new JButton("Export to CSV");
		exportBtn.setToolTipText("Export project list to CSV file.");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GuiHelpers.saveCsvDialog(new File("."), Project.projectListToCsv(projLstPanel.getProjects()));
				} catch(Exception ee) {
					ee.printStackTrace();
					GuiHelpers.showError(ee.getMessage());
				}
			}
		});

		JButton removeProjectBtn = new JButton("Remove project");
		removeProjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				projLstPanel.removeSelectedRow();
			}
		});

		JButton importSurveyProjsBtn = new JButton("Import survey projects");
		importSurveyProjsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CsvData data = GuiHelpers.loadCsvDialog(new File("data/benchmark"));
					if(data == null) {
						return;
					}
					List<ResponseProjects> respProjs = ResponseProjects.fromPreprocessedCsvData(data);
					List<Project> projs = new LinkedList<Project>();
					for(ResponseProjects rps : respProjs) {
						projs.addAll(rps.toProjectList());
					}
					projLstPanel.addProjects(projs);
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		final JButton tapTimelineBtn = new JButton("Tap Timeline");
		tapTimelineBtn.setToolTipText("Grab projects from the GitHub event timeline.");
		tapTimelineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tapTimelineTask == null) {
					tapTimelineTask = new ContinuousTask<List<Project>>() {
						@Override
						public void onSuccess(List<Project> result) {
							projLstPanel.addProjects(result);
						}

						@Override
						public List<Project> iterateInBackground() {
							try {
								return TimelineTapper.tapProjects();
							} catch(Exception e1) {
								e1.printStackTrace();
								GuiHelpers.showError(e1.getMessage());
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
		clearBtn.setToolTipText("Remove all projects from this list.");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				projLstPanel.clear();
				ownerField.setText("");
				repoField.setText("");
				userReposCombo.removeAllItems();
			}
		});

		bottomPane.add(importBtn);
		bottomPane.add(exportBtn);
		bottomPane.add(removeProjectBtn);
		bottomPane.add(remOfflineBtn);
		bottomPane.add(viewCsvBtn);
		bottomPane.add(importSurveyProjsBtn);
		bottomPane.add(tapTimelineBtn);
		bottomPane.add(clearBtn);
		bottomPane.add(settingsBtn);
		bottomPane.add(startBtn);

		add(bottomPane, ThreeRowGridBagConstraints.bottomPaneConstraints());
	}

	private SettingsWindow settingsWindow;
	@Override
	public void closeSettings() {
		settingsWindow = null;
	}
	private void tryShowSettingsWindow() {
		if(settingsWindow == null) {
			settingsWindow = new SettingsWindow(this);
			settingsWindow.setVisible(true);
		}
	}
}
