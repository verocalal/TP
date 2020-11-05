package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws{
	
	private final double G = 6.67E-11;
	
	public void apply(List<Body> Bodies) {
		double[] x = {0.0, 0.0};
		Vector vector_nulo = new Vector(x);
		for (int i = 0; i < Bodies.size(); i++) {
			if (Bodies.get(i).getM() == 0.0) {
				Bodies.get(i).setAcceleration(vector_nulo);
				Bodies.get(i).setVelocity(vector_nulo);
			}
			else  {
				double f;
				double m1 = masa(Bodies.get(i)); //masa cuerpo 1
				Vector F = new Vector(x);
				Vector direccion;
				Vector F_suma = new Vector(x);
				for (int j = 0; j < Bodies.size(); j++) {
					if (i != j) {
						double m2 = masa(Bodies.get(j)); //masa cuerpo 2 
						double longitud = calcularLongitud(Bodies.get(j), Bodies.get(i)); //longitud entre cuerpos
						double longitud_cuadrado = longitud * longitud;
						f = (m1 * m2 * G) / longitud_cuadrado; //calculo de fuerza de uno sobre otro
						direccion = new Vector(calcularDireccion(Bodies.get(j), Bodies.get(i)).direction()); //calculo de direccion
						F = new Vector(direccion.scale(f)); //calculo del vector fuerza
						F_suma = F_suma.plus(F); //sumatorio de fuerzas sobre un planeta
					}
				}
				double inverso_m = 1 / m1;
				Vector a = new Vector(F_suma.scale(inverso_m));
				Bodies.get(i).setAcceleration(a); //a = F * 1 / m
			}
		}
	}
	
	public double calcularLongitud(Body a, Body b) {
		return a.getPosition().distanceTo(b.getPosition());
	}
	
	public double masa(Body a) {
		return a.getM();
	}
	
	public Vector calcularDireccion(Body a, Body b) {
		return a.getPosition().minus(b.getPosition());
	}
	
	public String toString() {
		return "Implementa la ley de Newton, que cambia la aceleracion de los cuerpos constantemente";
	}


}