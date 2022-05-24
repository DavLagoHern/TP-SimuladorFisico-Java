package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public MassLosingBodyBuilder() {
		super("mlb", "A mass loosing type of  body");
	}

	@Override
	protected Body createTheInstance(JSONObject jsonObject) {
		if (jsonObject.getJSONArray("v").length() != 2 || jsonObject.getJSONArray("p").length() != 2 || jsonObject.getDouble("m") < 0) 
			throw new IllegalArgumentException();
		String id = jsonObject.getString("id");
		JSONArray p = jsonObject.getJSONArray("p");
		JSONArray v = jsonObject.getJSONArray("v");
		Vector2D position = new Vector2D(p.getDouble(0), p.getDouble(1));
		Vector2D velocity = new Vector2D(v.getDouble(0), v.getDouble(1));
		double mass = jsonObject.getDouble("m");
		double freq = jsonObject.getDouble("freq");
		double factor = jsonObject.getDouble("factor");
		
		
		Body x = new MassLosingBody(id, velocity, position, mass, factor, freq);
		
		return x;
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject info = new JSONObject();
		info.put("id", "the identifier");
		info.put("m", "mass");
		info.put("p", "position");
		info.put("v", "velocity");
		info.put("freq", "frecuency");
		info.put("factor", "factor");
		return info;
	}

}
