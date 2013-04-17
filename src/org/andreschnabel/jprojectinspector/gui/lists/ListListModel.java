package org.andreschnabel.jprojectinspector.gui.lists;

import javax.swing.*;
import java.util.List;

public class ListListModel<T> extends AbstractListModel {

	private List<T> lst;

	public ListListModel(List<T> lst) {
		this.lst = lst;
	}

	@Override
	public int getSize() {
		return lst.size();
	}

	@Override
	public Object getElementAt(int index) {
		return lst.get(index);
	}
}
