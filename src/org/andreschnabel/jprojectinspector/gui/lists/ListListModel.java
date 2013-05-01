package org.andreschnabel.jprojectinspector.gui.lists;

import javax.swing.*;
import java.util.List;

/**
 * Listenmodell für java.util.List.
 * @param <T> Typ der Elemente.
 */
public class ListListModel<T> extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	
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
