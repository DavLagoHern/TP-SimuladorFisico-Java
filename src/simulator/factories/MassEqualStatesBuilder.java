package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;


public class MassEqualStatesBuilder extends Builder<StateComparator> {

	public MassEqualStatesBuilder() {
		super("masseq", "A comparision between two masses");
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jsonObject) {
		MassEqualStates x = new MassEqualStates();
		return x;
		
	}

	@Override
    protected JSONObject createData() {
        JSONObject info = new JSONObject();
        return info;
    }
}
