package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder <T> {
	
	String _typeTag;
	String _desc;
	
	public Builder(String typeTag, String desc) {
		this._typeTag = typeTag;
		this._desc = desc;
	}
	
	public abstract T createTheInstance(JSONObject a);
	
	public double [] jsonArrayTodoubleArray(JSONArray a) {
		double[] da = new double[a.length()];
		for (int i = 0; i < da.length; i++)
			da[i] = a.getDouble(i); return da;
	}
	
	public T createInstance(JSONObject info) { 
		T b = null;
		if (_typeTag != null && _typeTag.equals(info.getString("type"))) 
			b = createTheInstance(info.getJSONObject("data"));
		return b; 
	}
	
	public JSONObject getBuilderInfo() { 
		JSONObject info = new JSONObject(); 
		info.put("type", _typeTag); 
		info.put("data", createData()); 
		info.put("desc", _desc);
		return info; 
	}
	
	public JSONObject createData() {
		return new JSONObject();
	}
	
	
}
