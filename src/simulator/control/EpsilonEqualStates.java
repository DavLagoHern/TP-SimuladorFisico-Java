package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {

	private double eps;

	public EpsilonEqualStates(double a) {
		eps = a;
	}

	public boolean equal(JSONObject s1, JSONObject s2) {
		if (s1.getDouble("time") == (s2.getDouble("time"))) {
			for (int i = 0; i < s1.length(); i++) {
				if (!idComp(s1, s2, i) || !massComp(s1, s2, i) || !positionComp(s1, s2, i) || !velocityComp(s1, s2, i) || !forceComp(s1, s2, i))
					return false;

			}
			return true;
		} 
		else return false;
	}

	public boolean idComp(JSONObject s1, JSONObject s2, int i) {
		return s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i)
				.getString("id"));
	}

	public boolean massComp(JSONObject s1, JSONObject s2, int i) {
		return Math.abs(s1.getJSONArray("bodies").getJSONObject(i).getDouble("m")
				- s2.getJSONArray("bodies").getJSONObject(i).getDouble("m")) <= eps;
	}

	public boolean positionComp(JSONObject s1, JSONObject s2, int i) {
		JSONArray a = s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("p");
		JSONArray b = s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("p");
		Vector2D p1 = new Vector2D(a.getDouble(0), a.getDouble(1));
		Vector2D p2 = new Vector2D(b.getDouble(0), b.getDouble(1));
		return (p1.distanceTo(p2) <= eps);
	}

	public boolean velocityComp(JSONObject s1, JSONObject s2, int i) {
		JSONArray a = s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v");
		JSONArray b = s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("v");
		Vector2D p1 = new Vector2D(a.getDouble(0), a.getDouble(1));
		Vector2D p2 = new Vector2D(b.getDouble(0), b.getDouble(1));
		return (p1.distanceTo(p2) <= eps);
	}

	public boolean forceComp(JSONObject s1, JSONObject s2, int i) {
		JSONArray a = s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f");
		JSONArray b = s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("f");
		Vector2D p1 = new Vector2D(a.getDouble(0), a.getDouble(1));
		Vector2D p2 = new Vector2D(b.getDouble(0), b.getDouble(1));
		return (p1.distanceTo(p2) <= eps);
	}

}
