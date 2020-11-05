package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body> {

	public MassLossingBodyBuilder () {
		super("mlb", "Mass Losing Body");
	}
	
	@Override
	public MassLossingBody createTheInstance(JSONObject a) {
		double[] vector_vacio = {0.0, 0.0};
		double[] p = jsonArrayTodoubleArray(a.getJSONArray("pos"));
		double[] v = jsonArrayTodoubleArray(a.getJSONArray("vel"));
		String id = a.getString("id");
		double m = a.getDouble("mass");
		int freq = a.getInt("freq");
		double factor = a.getDouble("factor");
		return new MassLossingBody(id, new Vector(v), new Vector(p), new Vector(vector_vacio), m, freq, factor);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("pos", "position vector");
		data.put("vel", "velocity vector");
		data.put("mass", "mass body");
		data.put("freq", "frequency lossing mass");
		data.put("factor", "factor lossing mass");
		return data;
	}

}
