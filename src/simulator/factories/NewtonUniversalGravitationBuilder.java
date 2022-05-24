package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's Universal Gravitational Law");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jsonObject) {
		double G = jsonObject.has("G") ? jsonObject.getDouble("G") : 6.67E-11;
		NewtonUniversalGravitation x = new NewtonUniversalGravitation(G);
		return x;
	}
	
	@Override
    protected JSONObject createData() {
        JSONObject info = new JSONObject();
        info.put("G", "Constant garavitational force");
        return info;
    }

}
