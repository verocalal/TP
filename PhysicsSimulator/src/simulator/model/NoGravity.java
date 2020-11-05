package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws {
	
	public void apply(List<Body> Bodies) {
		
	}
	
	public String toString() {
		return "Los cuerpos se mueven con aceleracion fija.";
	}

}
