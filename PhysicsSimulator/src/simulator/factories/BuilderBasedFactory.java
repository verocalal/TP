package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	private List<Builder<T>> _builders;
	private List<JSONObject> _factoryElements = new ArrayList<JSONObject>();
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this._builders = new ArrayList<Builder<T>>(builders);
		for (Builder <T> a: this._builders) {
			_factoryElements.add(a.getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T object = null;
		int i = 0;
		while (object == null && i < this._builders.size()) {
			object = this._builders.get(i).createInstance(info);
			i++;
		}
		if (object == null) throw new IllegalArgumentException("No existe el objeto JSON");
		return object;
	}

	@Override
	public List<JSONObject> getInfo() {
		return _factoryElements;
	}

}
