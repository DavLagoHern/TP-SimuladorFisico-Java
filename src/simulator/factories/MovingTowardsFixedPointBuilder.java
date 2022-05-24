package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "A body moving towards a fixed point");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jsonObject) {
		
		double g = jsonObject.has("g") ? jsonObject.getDouble("g") : 9.81;
		Vector2D centre;
		if (jsonObject.has("c")) {
			if (jsonObject.getJSONArray("c").length() != 2) throw new IllegalArgumentException();
			JSONArray c = jsonObject.getJSONArray("c");
			centre = new Vector2D(c.getDouble(0), c.getDouble(1));
		}
		else centre = new Vector2D();
		MovingTowardsFixedPoint x = new MovingTowardsFixedPoint(centre, g);
		return x;
	}
	
	@Override
    protected JSONObject createData() {
        JSONObject info = new JSONObject();
        info.put("g", "acceleration");
        info.put("c", "Point C");
        return info;
    }

	

}
