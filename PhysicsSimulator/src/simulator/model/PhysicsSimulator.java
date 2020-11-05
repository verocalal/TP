package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.model.GravityLaws;

public class PhysicsSimulator {
	
	private double dt;
	private GravityLaws law;
	private List<Body> body_list = new ArrayList<Body>();
	private List<SimulatorObserver> observer_list = new ArrayList<SimulatorObserver>();
	private double tiempo_actual;
	
	
	public PhysicsSimulator (double dt, GravityLaws law) throws IllegalArgumentException {
		if (dt <= 0.0 && law == null) {
			throw new IllegalArgumentException ("Los parametros estan mal definidos");
		}
		this.dt = dt;
		setGravityLaws(law);
		this.tiempo_actual = 0.0;
	}
	
	public void advance() {
		law.apply(body_list);
		for (int i = 0; i < body_list.size(); i++) {
			body_list.get(i).move(dt);;
		}
		tiempo_actual += dt;
		for (SimulatorObserver so: this.observer_list) {
			so.onAdvance(getBody_list(), getTiempo_actual());
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (body_list.contains(b)) {
			throw new IllegalArgumentException ("El cuerpo esta repetido");
		}
 		body_list.add(b);
 		for (SimulatorObserver so: this.observer_list) {
			so.onBodyAdded(getBody_list(), b);
		}
	}
	
	public String toString() {
		String sim = "{ \"time\": " + this.tiempo_actual + ", \"bodies\": [ ";
		for (int i = 0; i < body_list.size() - 1; i++) {
			sim += body_list.get(i) + ", ";
		}
		 
		sim += body_list.get(body_list.size() - 1) + " ] }";
		return sim;
	}
	
	public void reset() {
		this.body_list = new ArrayList<Body>();
		this.tiempo_actual = 0.0;
		for (SimulatorObserver so: this.observer_list) {
			so.onReset(new ArrayList<Body>(), 0, getDt(), law.toString());
		}
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException{
		if (dt > 0.0) this.dt = dt;
		else throw new IllegalArgumentException("Delta time invalido");
		for (SimulatorObserver so: this.observer_list) {
			so.onDeltaTimeChanged(dt);
		}
	}

	public void setGravityLaws(GravityLaws gravityLaws) throws IllegalArgumentException {
		if (gravityLaws != null) this.law = gravityLaws;
		else throw new IllegalArgumentException("Ley no valida");
		for (SimulatorObserver so: this.observer_list) {
			so.onGravityLawChanged(gravityLaws.toString());
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if (!this.observer_list.contains(o)) this.observer_list.add(o);
		for (SimulatorObserver so: this.observer_list) {
			so.onRegister(body_list, tiempo_actual, dt, law.toString());
		}
	}


	public double getDt() {
		return dt;
	}


	public void setDt(double dt) {
		this.dt = dt;
	}


	public GravityLaws getLaw() {
		return law;
	}


	public void setLaw(GravityLaws law) {
		this.law = law;
	}


	public List<Body> getBody_list() {
		return body_list;
	}


	public void setBody_list(List<Body> body_list) {
		this.body_list = body_list;
	}


	public double getTiempo_actual() {
		return tiempo_actual;
	}


	public void setTiempo_actual(double tiempo_actual) {
		this.tiempo_actual = tiempo_actual;
	}
	
	

}
