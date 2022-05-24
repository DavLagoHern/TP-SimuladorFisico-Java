package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	private List<Builder<T>> builders;
	private List<JSONObject> list;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<>(builders);
		list = new ArrayList<JSONObject>();
		
		for(int i = 0; i < builders.size(); i++) {
			list.add(builders.get(i).getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) {
		if(info == null) throw new IllegalArgumentException();
		boolean creado= false;
		int i = 0;
		T aux = null;
		while(!creado && i <builders.size()) {
			aux = builders.get(i).createInstance(info);
			if(aux != null) creado = true;
			i++;
		}
		return aux;
	}

	@Override
	public List<JSONObject> getInfo() {
		return list;
	}

}
