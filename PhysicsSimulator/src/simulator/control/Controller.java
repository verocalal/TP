package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	PhysicsSimulator _sim;
	Factory<Body> _bodiesFactory;
	Factory<GravityLaws> _lawsFactory;
	
	public Controller(PhysicsSimulator p, Factory<Body> f, Factory<GravityLaws> g) {
		_sim = p;
		_bodiesFactory = f;
		_lawsFactory = g;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in)); 
		JSONArray bodies = jsonInupt.getJSONArray("bodies");
		for (int i = 0; i < bodies.length(); i++) 
			_sim.addBody(_bodiesFactory.createInstance(bodies.getJSONObject(i)));
	}
	
	public void run(int n, OutputStream out) {
		out = System.out;
		PrintStream p = (out == null) ? null : new PrintStream(out);
		p.println("{\n\"states\": [");
		p.println(_sim + ",");
		for (int i = 0; i < n; i++) {
			this._sim.advance();
			p.print(_sim);
			if (i < n - 1)
				p.println(",");
		}
		p.println("\n]\n}");
		p.close();
	}
	
	public void reset() {
		this._sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		this._sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		this._sim.addObserver(o);
	}
	
	public void run(int n) {
		for (int i = 0; i < n; i++) this._sim.advance();
	}
	
	public Factory<GravityLaws> getGravityLawsFactory() {
		return this._lawsFactory;
	}
	
	public void setGravityLaws(JSONObject info) {
		this._sim.setGravityLaws(this._lawsFactory.createInstance(info));
	}

}
