package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {
		super("nf", "No force is applied");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jsonObject) {
		NoForce x = new NoForce();
		return  x;
	}
	
	@Override
    protected JSONObject createData() {
        JSONObject info = new JSONObject();
        return info;
    }

}
