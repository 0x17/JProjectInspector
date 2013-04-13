package org.andreschnabel.jprojectinspector.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.andreschnabel.jprojectinspector.model.Project;

public class InputWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private final ProjectListPanel projLstPanel;
	
	private JLabel ownerLbl, repoLbl;
	
	private final static int NCOLUMNS = 10;
	private final JTextField ownerField = new JTextField(NCOLUMNS);
	private final JTextField repoField = new JTextField(NCOLUMNS);

	public InputWindow() {
		super("Inputs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		initTopPane();

		projLstPanel = new ProjectListPanel();
		add(projLstPanel, BorderLayout.CENTER);
		
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
		
		setSize(640, 480);
		setLocationRelativeTo(null);
	}

	private void initTopPane() {
		JPanel topPane = new JPanel();
		topPane.setLayout(new FlowLayout());
		
		ownerLbl = new JLabel("owner:");
		topPane.add(ownerLbl);
		topPane.add(ownerField);
		
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
		
		add(topPane, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		new InputWindow().setVisible(true);
	}
	
}
