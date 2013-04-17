package org.andreschnabel.jprojectinspector.tests;

import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class VisualTest {

	protected abstract TestCallback[] getTests();

	@Test
	public void testVisual() throws Exception {
		TestCallback[] tests = getTests();
		for(TestCallback test : tests) {
			String description = test.getDescription();
			Helpers.log("Running visual test: " + description);
			test.invoke();
			int result = JOptionPane.showConfirmDialog (null, "Did '" + description + "' meet your expectations?","Visual Test Feedback", JOptionPane.YES_NO_OPTION);
			Assert.assertEquals("Visual test: " + description, JOptionPane.YES_OPTION, result);
		}
	}

	private static Object lock = new Object();

	private static class TestFrame extends JFrame {
		private static final long serialVersionUID = 1L;

		public TestFrame() {
			super("TestFrame");
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setLayout(new FlowLayout());
			setSize(400, 400);
			setLocationRelativeTo(null);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					synchronized (lock) {
						setVisible(false);
						lock.notify();
					}
				}
			});
		}
	}

	protected static JFrame getTestFrame() {
		TestFrame frm = new TestFrame();
		frm.setVisible(true);
		return frm;
	}

	protected static void waitForFrameToClose(final JFrame frame) throws Exception {
		frame.invalidate();
		frame.validate();
		frame.repaint();
		Runnable waitRunner = new Runnable() {
			@Override
			public void run() {
				synchronized(lock) {
					while(frame.isVisible()) {
						try {
							lock.wait();
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
					frame.dispose();
				}
			}
		};
		final Thread t = new Thread(waitRunner);
		t.start();
		t.join();
	}

}
