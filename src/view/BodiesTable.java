package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.model.Body;

public class BodiesTable extends JPanel {

	private static final long serialVersionUID = 1L;
	private BodiesTableModel tableModel;
	private Controller _ctrl;
	List<Body> _bodiesList;

	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));
	
		_ctrl = ctrl;
		initGUI();
	}
	
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout(5,5));	
		this.tableModel = new BodiesTableModel(_ctrl);
		JTable table = new JTable(this.tableModel);
		
		mainPanel.add(new JScrollPane(table));
		table.setFillsViewportHeight(true);
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	
}
