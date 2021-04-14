package de.amr.schule.graphdrawing.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.api.GraphDrawingViewController;

public class PointsTableView extends JPanel implements GraphDrawingViewController {

	private static final String[] COLUMN_NAMES = { "#", "x", "f(x)" };

	private final GraphDrawingModel model;
	private final PointsTableModel tableModel;
	@SuppressWarnings("unused")
	private GraphDrawingController controller;

	private class PointsTableModel extends AbstractTableModel {

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
				return rowIndex + 1;
			case 1:
				return model.getPoints().get(rowIndex).x;
			case 2:
				return model.getPoints().get(rowIndex).fx;
			default:
				return "";
			}
		}
	}

	public PointsTableView(GraphDrawingModel model) {
		this.model = model;
		tableModel = new PointsTableModel();
		setLayout(new BorderLayout());
		add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
	}

	@Override
	public void setController(GraphDrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void update() {
		tableModel.fireTableDataChanged();
	}
}
