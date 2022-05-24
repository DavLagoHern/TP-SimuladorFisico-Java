package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private boolean _stopped;
	private JSpinner _steps;
	private JTextField _deltaTime;
	private JButton run;
	private JButton exit;
	private JButton setLaw;
	private JButton load;
	private JButton stop;
	private JPanel mainPanel;
	private ForceLawsDialog forceLawsDialog;
	private List <String> laws;
	
	public ControlPanel(Controller ctr) {
		_ctrl = ctr;
		_stopped = true;
		laws = new ArrayList<String>();
		laws.add("Newton's Universal Gravitational Law");
		laws.add("Moving Toward Fixed Point");
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		mainPanel = new JPanel(new BorderLayout(100, 5));

		JToolBar toolBar = new JToolBar();		//alineacion, espacio horizontal, vertical
		toolBar.setLayout(new BorderLayout(100, 5));
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
		
		JPanel izq = new JPanel(new FlowLayout(1, 5, 5));
		createIzq(izq);
		toolBar.add(izq, BorderLayout.WEST);
		
		
		JPanel der = new JPanel(new FlowLayout(1, 5, 5));
		createDer(der);
		toolBar.add(der, BorderLayout.EAST); 

		this.add(mainPanel);
		
	}
	
	private void run_sim(int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(Integer.valueOf(String.valueOf(_steps.getValue())), null, null, null);
			}
			catch (Exception e) {
				exit.setEnabled(true);
				load.setEnabled(true);
				setLaw.setEnabled(true);
				//TODO enable all buttons
				//TODO show error in a dialog box
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
				run_sim(n-1);
				}
				});
		}else {
			_stopped = true;
			//TODO enable all buttons
		}
	}
	
	private void createDer(JPanel der) {
		exit = exitButton();
		der.add(exit);
	}
	
	private void createIzq(JPanel izq) {
		load = loadButton();
		izq.add(load); 
		
		 setLaw = setLawButton();
		izq.add(setLaw); 
		
		run = runButton();
		izq.add(run); 
		
		stop = stopButton();
		izq.add(stop); 
		
		
		izq.add( new JLabel("Steps: "));
		
		_steps = stepsText();
		izq.add(_steps);
		
		izq.add( new JLabel("Delta-Time: "));
		
		_deltaTime = deltaTimeText();
		izq.add(_deltaTime);	
	}
	
	private JSpinner stepsText() {
		_steps = new JSpinner(new SpinnerNumberModel(8000, 1, 10000, 100));
		_steps.setToolTipText("Simulation tick to run: 1-10000");
		_steps.setPreferredSize(new Dimension(80, 40));
		return _steps;
	}
	
	private JTextField deltaTimeText() {
		_deltaTime = new JTextField();
		_deltaTime.setText("20");
		_deltaTime.setPreferredSize(new Dimension(80, 40));
		return _deltaTime;
	}
	
	private JButton loadButton() {
		JButton load = new JButton();
		load.setActionCommand("load");
		load.setToolTipText("Load a file");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				_ctrl.reset();
				JFileChooser fc = new JFileChooser();
				int selection = fc.showOpenDialog(null);
				if (selection == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						InputStream is = new FileInputStream(file);
						_ctrl.loadBodies(is);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				else System.out.println("load cancelled by user");
					
				
			}
		});
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		load.setPreferredSize(new Dimension(45, 45));
		return load;
		
	}
	
	
	private JButton stopButton() {
		JButton stop = new JButton();
		stop.setActionCommand("setLaw");
		stop.setToolTipText("Law");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				_stopped = true;
				exit.setEnabled(true);
				load.setEnabled(true);
				setLaw.setEnabled(true);
			}
		});
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.setPreferredSize(new Dimension(45, 45));
		return stop;
	}
	
	private JButton runButton() {
		JButton run = new JButton();
		run.setActionCommand("setLaw");
		run.setToolTipText("Law");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				exit.setEnabled(false);
				load.setEnabled(false);
				setLaw.setEnabled(false);
				_stopped = false;
				_ctrl.setDeltaTima(Double.parseDouble(_deltaTime.getText()));
				run_sim(Integer.parseInt(_steps.getValue().toString()));
			}
		});
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.setPreferredSize(new Dimension(45, 45));
		return run;
	}
	
	JButton setLawButton() {
		setLaw = new JButton();
		setLaw.setActionCommand("setLaw");
		setLaw.setToolTipText("Law");
		setLaw.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent a) {
				if (forceLawsDialog == null) {
					forceLawsDialog = new ForceLawsDialog(_ctrl, laws);
				}
				int status = forceLawsDialog.open();
				System.out.println("ESTADO: " + status);
				if (status == 1) {
					try {
						JSONObject newLaw = forceLawsDialog.getLaw();
						_ctrl.setForceLaws(newLaw);
						System.out.println(newLaw.get("G"));
						}
					catch(Exception e) {
						
					}
				}
			}
		});
		setLaw.setIcon(new ImageIcon("resources/icons/physics.png"));
		setLaw.setPreferredSize(new Dimension(45, 45));
		return setLaw;
	}
	
	JButton exitButton() {
		exit = new JButton();
		exit.setActionCommand("setLaw");
		exit.setToolTipText("Law");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				Object[] options = {"Si", "No"};
				int n = JOptionPane.showOptionDialog(mainPanel, "Está seguro de que quiere cerrar el Simulador?", "Mis cojones",
						 JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
				if (n == JOptionPane.YES_OPTION) System.exit(0);
			}
		});
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		exit.setPreferredSize(new Dimension(45, 45));
		return exit;
	}
	
	
	//------------------------------------------------------
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
}



