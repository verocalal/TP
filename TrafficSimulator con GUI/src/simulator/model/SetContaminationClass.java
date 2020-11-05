package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetContaminationClass extends Event{

	private List<Pair<String,Integer>> lista;
	private int id;
	private StringBuilder co2;
	public SetContaminationClass(int time,List<Pair<String,Integer>>cs) throws IllegalArgumentException{
		super(time);
		if(cs == null)
			throw new IllegalArgumentException("Excepcion en constructor de setContamination class");
		else
			lista = new ArrayList<Pair<String, Integer>>(cs);
		
		//lo de abajo esta para dar formato al evento en la tabla EventsTableModel
		co2 = new StringBuilder();
		co2.append("[");
		System.out.println(lista.size());
		for(int i=0;i<lista.size();i++)
		{
			co2.append("(");
			co2.append(lista.get(i).getFirst());
			co2.append(",");
			co2.append(lista.get(i).getSecond());
			co2.append(")");
			co2.append(",");

		}
		co2.append("]");
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException
	{

		for(Pair<String, Integer> par : lista)
		{
			Vehicle v = map.getVehicle(par.getFirst());
			if(v == null)
				throw new IllegalArgumentException("Excepcion en el metodo execute de setContaminationClass");
			else
				v.setContaminacionTotal(par.getSecond());
		}
	}
	
	public String toString()
	{
		return "Set contamination ' " + co2.toString() + " ' ";
	}

}
