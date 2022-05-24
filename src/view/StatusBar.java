package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		JPanel statusBar = new JPanel(new FlowLayout(0, 50, 5));
		this.setLayout(new BorderLayout(290, 290));
		statusBar.setBorder(BorderFactory.createBevelBorder(1));
		_currTime = new JLabel("Time: 0.0");
		_currLaws = new JLabel("Law: " );
		_numOfBodies = new JLabel("huevos");
		statusBar.add(_currTime);
		statusBar.add(_numOfBodies);
		statusBar.add(_currLaws);
		this.add(statusBar);
		
	}
	
	private void update() {
	}

//----------------------------------------------------------
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime = new JLabel(Double.toString(time));
		_currLaws = new JLabel(fLawsDesc);
		_numOfBodies = new JLabel(Integer.toString(bodies.size()));
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText(Double.toString(time));
		_currLaws.setText(fLawsDesc);
		_numOfBodies.setText(Integer.toString(bodies.size()));

		// TODO Auto-generated method stub

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_currTime.setText(Double.toString(time));
		_numOfBodies.setText(Integer.toString(bodies.size()));
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText(fLawsDesc);

		// TODO Auto-generated method stub

	}
}
