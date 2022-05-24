
package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	private PhysicsSimulator simulator;
	private Factory<Body> bodyFactory;
	private Factory<ForceLaws> fLawsFactory;

	public Controller(PhysicsSimulator ps, Factory<Body> fc, Factory<ForceLaws> fl) {
		simulator = ps;
		bodyFactory = fc;
		fLawsFactory = fl;
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInput.getJSONArray("bodies");

		for (int i = 0; i < bodies.length(); i++) {
			JSONObject aux = bodies.getJSONObject(i);
			simulator.addBody(bodyFactory.createInstance(aux));
		}

	}

	public void run(int steps, OutputStream out, InputStream in, StateComparator cmp) throws NotEqualStatesException {
		JSONObject expOut = null;
		if (in != null)
			expOut = new JSONObject(new JSONTokener(in));

		if (out == null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
				}
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		JSONObject estadoActual = null;
		JSONObject expState = null;
		estadoActual = simulator.getState(); //Comparacion de los estados base
		p.println(estadoActual);
		if (expOut != null) {
			expState = expOut.getJSONArray("states").getJSONObject(0);
			if (!cmp.equal(expState, estadoActual))  throw new NotEqualStatesException(expState, estadoActual, 0);
		}
		for (double i = 1; i < steps; i++) {
			simulator.advance();
			estadoActual = simulator.getState(); //Comparacion de los estados base
			p.println("," + estadoActual);

			if (expOut != null) {
				expState = expOut.getJSONArray("states").getJSONObject((int) i);
				if (!cmp.equal(expState, estadoActual))  throw new NotEqualStatesException(expState, estadoActual, i);
			}
			

		}
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		simulator.reset();
	}
	
	public void setDeltaTima(double dt) {
		simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return fLawsFactory.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		ForceLaws newFL = fLawsFactory.createInstance(info);
		simulator.setForceLaws(newFL);
	}
}

