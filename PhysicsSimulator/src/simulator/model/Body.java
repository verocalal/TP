package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected String id;
	protected Vector v;
	protected Vector p;
	protected Vector a;
	protected double c;
	protected double m;
	
	public Body(String id, Vector v, Vector p, Vector a, double m) {
		this.id = id;
		this.v = v;
		this.p = p;
		this.a = a;
		this.m = m;
		this.c = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vector getVelocity() {
		return new Vector(this.v);
	}

	public void setVelocity(Vector v) {
		this.v = new Vector(v);
	}

	public Vector getPosition() {
		return new Vector(this.p);
	}

	public void setPosition(Vector p) {
		this.p = new Vector(p);
	}

	public Vector getAcceleration() {
		return new Vector(this.a);
	}

	public void setAcceleration(Vector a) {
		this.a = new Vector(a);
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}
	
	void move(double t) {
		this.p = p.plus(v.scale(t).plus((a.scale((t*t)/2))));
		this.v = v.plus(a.scale(t));
	}
	
	public String toString() {
		return "{  \"id\": \"" + this.id + "\", \"mass\": " + this.m + ", \"pos\": " + this.p +", \"vel\": " + this.v +", \"acc\": " + this.a + " }";
	}
	
}
