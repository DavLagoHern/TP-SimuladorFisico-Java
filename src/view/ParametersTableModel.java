package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.SimulatorObserver;

public class ParametersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private String[] columns = { "Key", "Value", "Description" };

	private List<InfoDataTable> _data;
	
	
	public ParametersTableModel() {

		_data = new ArrayList<InfoDataTable>();
		_data.add(new InfoDataTable("G" ,""," Universal Gravitational Constant"));
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return _data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		InfoDataTable info = _data.get(row);
		
		String s = "";

		switch (col) {
		case 0:
			s = info.getId();
			break;
		case 1:
			s = info.getValue();
			break;
		case 2:
			s = info.getDesc();
			break;
		}

		return s;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return (col == 1);
	}
	
	@Override
	public void setValueAt(Object o, int row, int col) {
		InfoDataTable info = _data.get(row);
		if (col==1) info.setValue(o.toString());

			
	}


	public void clear() {
		_data.clear();
		this.fireTableStructureChanged();
		
	}

	public void addRow(int index) {
		_data.clear();
		if (index == 0) _data.add(new InfoDataTable("G" ,""," Universal Gravitational Constant"));
		else if(index == 1) {
			_data.add(new InfoDataTable("c" ,"","The point which the bodies are going to move towards (ex: [100.0, 50.0])"));
			_data.add(new InfoDataTable("g" ,"","The length of the acceleration vector"));
		}
		this.fireTableDataChanged();
	}

	public JSONObject getLaw() {
		JSONObject newLaw = new JSONObject();
		JSONObject data = new JSONObject();
		if(getRowCount() == 1) {
			data.put("G", _data.get(0).getValue());
			newLaw.put("type", "nlug");
			newLaw.put("data", data);
			newLaw.put("desc", _data.get(0).getDesc());
		}
		else if (getRowCount() == 2){
			data.put("c", _data.get(0).getValue());
			data.put("g", _data.get(1).getValue());
			
			newLaw.put("type", "mtfp");
			newLaw.put("data", data);
			newLaw.put("desc", _data.get(0).getDesc());
		}
		
		return newLaw;
	}

	
	}
	

