package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.ForceLaws;

public class PhysicsSimulator {
	private double actualTime;
	private ForceLaws forceLaws;
	private List<Body> listBodys;
	private List<SimulatorObserver> observerList;
	private double dt;

	public PhysicsSimulator(ForceLaws a, double time) throws IllegalArgumentException{
		if (time <= 0 || a == null) throw new IllegalArgumentException();
		actualTime = 0.0;
		forceLaws = a;
		dt = time;
		listBodys = new ArrayList<Body>();
		observerList = new ArrayList<SimulatorObserver>();
	}

	public void addBody(Body newBody) {
		if (listBodys.contains(newBody)) throw new IllegalArgumentException();
		else {
			listBodys.add(newBody);
			for (SimulatorObserver ob: observerList) {
				 ob.onBodyAdded(listBodys, newBody); 
				 //System.out.println(ob);
				 }
		}
	}

	public void advance() {
		for (Body b : listBodys)
			b.resetForce();

		forceLaws.apply(listBodys);
		
		for (Body b : listBodys)
			b.move(dt);
		
		actualTime += dt;
		for (SimulatorObserver ob: observerList) {
			 ob.onAdvance(listBodys, actualTime); 
			 //System.out.println(ob);
			 }
	}



	public JSONObject getState() {
		JSONObject info = new JSONObject();
		JSONArray listaJSON = new JSONArray();
		for(Body a : listBodys) 
			listaJSON.put(a.getState());
		
		info.put("time", actualTime);
		info.put("bodies", listaJSON);
		return info;
		
	}

	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		listBodys = new ArrayList<Body>();
		actualTime = 0.0;
		for (SimulatorObserver ob: observerList) {
			 ob.onReset(listBodys, actualTime, dt, forceLaws.toString()); 
			 //System.out.println(ob);
			 }

	}
	
	public void setDeltaTime(double dt) {
		if (dt < 0) throw new IllegalArgumentException();
		else {
			this.dt = dt;
			for (SimulatorObserver ob: observerList) {
				 ob.onDeltaTimeChanged(dt); 
				 //System.out.println(ob);
				 }
		}
	}
	
	public void setForceLaws(ForceLaws forceLaws) {
		if (forceLaws == null) throw new IllegalArgumentException();
		else {
			this.forceLaws = forceLaws;
			for (SimulatorObserver ob: observerList) {
				 ob.onForceLawsChanged(forceLaws.toString()); 
				 //System.out.println(ob);
				 }
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if (observerList.contains(o)) throw new IllegalArgumentException();
		else {
			observerList.add(o);
			o.onRegister(listBodys, actualTime, dt, forceLaws.toString()); 
		}

	}
}
