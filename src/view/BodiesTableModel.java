package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private List<Body> _bodiesInTable;
	
	private String[] columnNames = { "Id", "Mass", "Position", "Velocity", "Force" };
	private Controller _ctrl;
	
	private int Colum;

	public BodiesTableModel(Controller ctrl) {
		Colum =5;
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		_bodiesInTable = new ArrayList<Body>();
	}
	@Override
	public int getRowCount() {
		return _bodiesInTable.size();
	}

	@Override
	public int getColumnCount() {
		return Colum;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object aux = null;
		switch (columnIndex) {
		case 0:
			aux =  _bodiesInTable.get(rowIndex).getId();
			break;
		case 1:
			aux = _bodiesInTable.get(rowIndex).getMass();
			;
			break;
		case 2:
			aux = _bodiesInTable.get(rowIndex).getPosition();
			break;
		case 3: 
			aux =  _bodiesInTable.get(rowIndex).getVelocity();
		case 4: 
			aux =  _bodiesInTable.get(rowIndex).getForce();
			
		default:
			assert (false); // should be unreachable
		}

		return aux;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodiesInTable = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodiesInTable = bodies;
		fireTableStructureChanged();

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodiesInTable = bodies;
		fireTableStructureChanged();

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodiesInTable = bodies;
		fireTableStructureChanged();

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub

	}
	

}
