package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws {
	
	private final double gravity = 9.81;
	private Vector o;
	private Vector d;
	
	public FallingToCenterGravity() {
		double[] x = {0.0, 0.0};
		this.o = new Vector(x);
	}

	public void apply(List<Body> Bodies) {
		for (int i = 0; i < Bodies.size(); i++) {
			d = o.minus(Bodies.get(i).getPosition().direction()).direction();
			Bodies.get(i).setAcceleration(d.scale(this.gravity));
		}
	}
	
	public String toString() {
		return "Los cuerpos tienen una aceleración fija de g = 9.81 en dirección al origen."; 
	}

}
