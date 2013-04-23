package org.andreschnabel.jprojectinspector.gui.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BenchmarkTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private Color okColor = new Color(0.0f, 0.6f, 0.0f);
	private Color failColor = new Color(0.6f, 0.0f, 0.0f);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		try {
			if(value instanceof String) {
				String sval = (String)value;
				if(sval.contains(" (OK)")) {
					cell.setForeground(okColor);
				}
				else if(sval.contains(" (FAIL)")) {
					cell.setForeground(failColor);
				} else {
					cell.setForeground(Color.BLACK);
				}
			} else {
				cell.setForeground(Color.BLACK);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cell;
	}
}
