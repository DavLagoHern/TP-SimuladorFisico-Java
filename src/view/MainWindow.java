package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

/*import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;*/
import javax.swing.*;

import simulator.control.Controller;

import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

//Vamos a sacar los datos de un ArrayList en un JTable
//Para esto necesitamos un modelo de tabla.
//Pues no siempre los datos van a venir en un array bidimensional
//
// In this example we will show the information stored in an List using
// a JTable
public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller ctrl;



	public MainWindow(Controller _ctrl) {

		super("Physics Simulator");
		ctrl = _ctrl;

		initGUI();
	}

	public void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		ControlPanel ctrlPanel = new ControlPanel(ctrl);
		StatusBar stBar = new StatusBar(ctrl);
		
		Viewer physicsViewer = new Viewer(ctrl);
		physicsViewer.setPreferredSize(new Dimension (800, 600));
		
		BodiesTable bdsTable = new BodiesTable(ctrl);
		bdsTable.setPreferredSize(new Dimension(800,300));
		
		mainPanel.add(ctrlPanel);
		mainPanel.add(bdsTable);
		mainPanel.add(new JScrollPane(physicsViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		mainPanel.add(stBar);
		
		this.setResizable(false);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //tiene que ser do nothing
		this.pack();
		this.setVisible(true);
	}


	}

	



	


