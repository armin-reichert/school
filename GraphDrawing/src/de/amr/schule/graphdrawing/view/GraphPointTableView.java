package de.amr.schule.graphdrawing.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;

public class GraphPointTableView extends JPanel implements IView {

	private static final String[] COLUMN_NAMES = { "x", "f(x)" };

	private final GraphDrawingModel model;
	private final GraphPointTableModel tableModel;

	private class GraphPointTableModel extends AbstractTableModel {

		@Override
		public int getRowCount() {
			return model.getPoints().size();
		}

		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return model.getPoints().get(rowIndex).x;
			case 1:
				return model.getPoints().get(rowIndex).fx;
			}
			return "";
		}
	}

	public GraphPointTableView(GraphDrawingModel model, GraphDrawingController controller) {
		this.model = model;
		tableModel = new GraphPointTableModel();
		setLayout(new BorderLayout());
		add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
	}

	@Override
	public void update() {
		tableModel.fireTableDataChanged();
	}
}
