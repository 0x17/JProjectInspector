package org.andreschnabel.jprojectinspector.gui;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InputWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private final ProjectTablePanel projLstPanel;
	
	private JLabel ownerLbl, repoLbl;
	
	private final static int NCOLUMNS = 10;
	private final JTextField ownerField = new JTextField(NCOLUMNS);
	private final JTextField repoField = new JTextField(NCOLUMNS);
	private KeyListener keyListener = new QuitOnEscapeKeyListener();
	private DocumentListener docListener = new EmptyReposComboOnChange();
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
	private final JComboBox<String> userReposCombo = new JComboBox<String>();

	private JButton addAllBtn = new JButton("+All");

	public InputWindow() {
		super("Inputs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(0,0));
		
		initTopPane();

		projLstPanel = new ProjectTablePanel();
		add(projLstPanel, BorderLayout.CENTER);
		
		initBottomPane();
		
		setSize(750, 540);
		setLocationRelativeTo(null);
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
		
		JButton queryProjsBtn = new JButton("Query");
		queryProjsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String owner = ownerField.getText();
				if(owner == null || owner.isEmpty()) return;
				
				userReposCombo.removeAll();
				
				if(ProjectDownloader.isUserOnline(owner)) {
					try {
						List<Project> scrapedProjs = UserScraper.scrapeProjectsOfUser(owner);
						for(Project p : scrapedProjs) {
							userReposCombo.addItem(p.repoName);
						}						
						userReposCombo.setVisible(true);
						addAllBtn.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		topPane.add(queryProjsBtn);
		
		userReposCombo.setEditable(false);
		topPane.add(userReposCombo);
		userReposCombo.setVisible(false);
		userReposCombo.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ie) {
				String repoName = (String)ie.getItem();
				if(repoName != null && !repoName.isEmpty()) {
					repoField.setText(repoName);
				}
			}
		});

		addAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String owner = ownerField.getText();
				for(int i=0; i<userReposCombo.getItemCount(); i++) {
					String repo = userReposCombo.getItemAt(i);
					projLstPanel.addProject(new Project(owner, repo));
				}
			}
		});
		addAllBtn.setVisible(false);
		topPane.add(addAllBtn);

		add(topPane, BorderLayout.NORTH);
		addKeyListenerToPanel(topPane);
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
		JPanel bottomPane = new JPanel(new FlowLayout());
		bottomPane.add(remOfflineBtn);
		bottomPane.add(startBtn);
		add(bottomPane, BorderLayout.SOUTH);
		addKeyListenerToPanel(bottomPane);
	}
	
	private void addKeyListenerToPanel(JPanel p) {
		for(Component c : p.getComponents()) {
			c.addKeyListener(keyListener);
		}
	}
	
	public static void main(String[] args) {
		new InputWindow().setVisible(true);
	}
	
}
