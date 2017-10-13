package de.amr.schule.graphdrawing.view;

import javax.swing.table.AbstractTableModel;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;

public class GraphPointTableModel extends AbstractTableModel {
	
	private GraphDrawingModel model;
	
	public GraphPointTableModel(GraphDrawingModel model) {
		this.model = model;
	}

	@Override
	public int getRowCount() {
		return model.getPoints().size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "x";
		} else if (column == 1) {
			return "f(x)";
		}
		return "";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return model.getPoints().get(rowIndex).x;
		} else if (columnIndex == 1) {
			return model.getPoints().get(rowIndex).fx;
		}
		return "";
	}
}
