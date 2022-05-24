package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	

	private Controller _ctrl;
	private int state;
	ParametersTableModel paramTableModel;
	


	

	
	private JComboBox comboBox;
	
	public ForceLawsDialog(Controller ctrl, List<String> lawList){
		super();
		this.setTitle("Force Laws Selection");
		_ctrl = ctrl;
		this.state = 0;
		initGUI(lawList);
		//this.setBounds(400, 400, 600, 600);
	}
	
	private void initGUI(List<String> lawList) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel top = new JPanel(new FlowLayout());
		JPanel mid = new JPanel(new BorderLayout());
		JPanel down = new JPanel(new BorderLayout());
		
		JPanel comboPanel = new JPanel(new FlowLayout());
		//JPanel buttons = new JPanel (new BorderLayout());
		
		JLabel text = new JLabel("<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		text.setLayout(new FlowLayout());
	
		top.add(text);
		
		
		paramTableModel = new ParametersTableModel();
		JTable paramTable = new JTable(paramTableModel);
	    JScrollPane scroll = new JScrollPane(paramTable);
	    mid.add(scroll, BorderLayout.CENTER);
		
		this.comboBox = new JComboBox<String>();
		for (int i=0; i < lawList.size(); i++){
			this.comboBox.addItem(lawList.get(i));
		}
		
		this.comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int index = comboBox.getSelectedIndex();
				paramTableModel.addRow(index);	
				
			}
			
		});
		
	
		
		
		
		comboPanel.add(new JLabel("Force Law: "));
		comboPanel.add(comboBox);
		down.add(comboPanel, BorderLayout.CENTER);
		down.add(buttons(), BorderLayout.SOUTH);

		mainPanel.add(top, BorderLayout.NORTH);
		mainPanel.add(mid, BorderLayout.CENTER);
		mainPanel.add(down,BorderLayout.SOUTH);
		
		
		
	     this.add(mainPanel);
	     this.setVisible(false); 
	     this.pack();
	     this.setModal(true);

		
	}

	
	private JPanel buttons() {
		JPanel buttonsPanel = new JPanel(new FlowLayout()); 
		JButton OK = new JButton("OK");
		JButton Cancel = new JButton("Cancel");
		buttonsPanel.add(Cancel);
		buttonsPanel.add(OK);
		
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = 0;
				ForceLawsDialog.this.setVisible(false);
				
			}
			
		});
		
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = 1;
				ForceLawsDialog.this.setVisible(false);
				
			}
			
		});
		
		
		
		return buttonsPanel;
	}
	
	
	public int open() {
		pack();
		setVisible(true);
		return state;
	}

	public JSONObject getLaw() {
		return paramTableModel.getLaw();
		
	}
	
}
