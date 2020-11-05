package simulator.model;

import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@SuppressWarnings("null")
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) 
	{	
		
		List<Vehicle> aux = null;
		if(!q.isEmpty())
		{
			Vehicle v= q.get(0);
			aux.add(v);
		}
		return aux;

	}

}
