package com.dev.frontend.panels.list;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.dev.frontend.panels.BusinessPresenter;
import com.dev.frontend.services.Services;

public abstract class ListDataModel extends DefaultTableModel implements BusinessPresenter {
	private static final long serialVersionUID = -4086300183434090161L;

	public ListDataModel(String[] columnNames, int rowsCount) {
		super(columnNames, rowsCount);
	}

	@Override
	public boolean bindToGUI(Object o) {
		clear();

		final List<Object> list = Services.listCurrentRecords(getObjectType());
		final String[][] modelData = convertRecordsListToTableModel(list);

		for (final String[] row : modelData) {
			addRow(row);
		}
		return true;
	}

	@Override
	public void clear() {
		setRowCount(0);
	}

	@Override
	public void onInit() {
		bindToGUI(null);
	}

	@Override
	public Object guiToObject() {
		return getDataVector();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public abstract int getObjectType();

	public abstract String[][] convertRecordsListToTableModel(List<Object> list);
}
