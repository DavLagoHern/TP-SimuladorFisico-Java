package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String _typeTag;
	protected String _desc;
	
	public Builder(String type, String desc) {
		_typeTag = type;
		_desc = desc;
	}

	
	protected abstract T createTheInstance(JSONObject jsonObject);

	public T createInstance(JSONObject info) {
		T aux = null;
		if ( _typeTag != null && _typeTag.equals(info.getString("type"))) {
			aux = createTheInstance(info.getJSONObject("data"));
		}
		return aux;
		
	}

	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("data", createData());
		info.put("desc", _desc);
		return info;
	}
	
	protected JSONObject createData() {
		return new JSONObject();
	}
	
	

}
