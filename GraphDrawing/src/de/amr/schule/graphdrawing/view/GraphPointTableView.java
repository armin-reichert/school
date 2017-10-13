package de.amr.schule.graphdrawing.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;

@SuppressWarnings("serial")
public class GraphPointTableView extends JPanel implements IGraphDrawingView {

	private JTable table;
	private GraphPointTableModel tableModel;

	public GraphPointTableView(GraphDrawingModel model, GraphDrawingController controller) {
		tableModel = new GraphPointTableModel(model);
		table = new JTable(tableModel);
		add(new JScrollPane(table));
	}

	@Override
	public void update() {
		tableModel.fireTableDataChanged();
	}
}
