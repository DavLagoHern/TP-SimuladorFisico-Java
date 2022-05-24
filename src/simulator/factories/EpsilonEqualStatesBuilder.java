package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

	public EpsilonEqualStatesBuilder() {
		super("epseq", "A state comparator with a number epsilon");
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jsonObject) {
		double eps=jsonObject.has("eps") ? jsonObject.getDouble("eps") : 0.0;
		//if (jsonObject.getDouble("eps") < 0) throw new IllegalArgumentException();
		EpsilonEqualStates x = new EpsilonEqualStates(eps);
		return x;
	}
	
	@Override
    protected JSONObject createData() {
        JSONObject info = new JSONObject();
        info.put("eps", "epsilon to compare");
        return info;
    }

}
