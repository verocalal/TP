package simulator.model;

import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> aux = null;
		for(Vehicle v : q)
			aux.add(v);
		return aux;
	}

}
