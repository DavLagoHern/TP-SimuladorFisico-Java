package simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if (s1.get("time") == s2.get("time")) {
			for (int i = 0; i < s1.length(); i++) {
				if (!idComp(s1, s2, i) || !massComp(s1, s2, i))
					return false;
			}
			return true;
		} 
		else return false;
	}

	public boolean idComp(JSONObject s1, JSONObject s2, int i) {
		return s1.getJSONArray("bodies").getJSONObject(i).get("id") == s2.getJSONArray("bodies").getJSONObject(i).get("id");
	}

	public boolean massComp(JSONObject s1, JSONObject s2, int i) {
		return s1.getJSONArray("bodies").getJSONObject(i).get("m") == s2.getJSONArray("bodies").getJSONObject(i).get("m");
	}
}
