package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		super("ng", "No Gravity");
	}
	
	public GravityLaws createTheInstance(JSONObject a) {
		return new NoGravity();
	}
}
