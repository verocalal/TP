package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "Default Body");
	}
	
	public Body createTheInstance(JSONObject a) {
		double[] vector_vacio = {0.0, 0.0};
		double[] p = jsonArrayTodoubleArray(a.getJSONArray("pos"));
		double[] v = jsonArrayTodoubleArray(a.getJSONArray("vel"));
		String id = a.getString("id");
		double m = a.getDouble("mass");
		return new Body(id, new Vector(v), new Vector(p), new Vector(vector_vacio), m);
	}
	
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("pos", "position vector");
		data.put("vel", "velocity vector");
		data.put("mass", "mass body");
		return data;
	}

}
