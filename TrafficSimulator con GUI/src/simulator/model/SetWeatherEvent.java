package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String,Weather>> lista;
	private int id;
	private StringBuilder weather;
	
	public SetWeatherEvent(int time,List<Pair<String,Weather>>ws) throws IllegalArgumentException{
		super(time);
		if(ws== null)
			throw new IllegalArgumentException("Excepcion en constructor de setWeatherEvent");
		else
			lista = new ArrayList<Pair<String, Weather>>(ws);
		
		weather = new StringBuilder();
		weather.append("[");
		for(int i=0;i<lista.size();i++)
		{
			weather.append("(");
			weather.append(lista.get(i).getFirst());
			weather.append(",");
			weather.append(lista.get(i).getSecond());
			weather.append(")");
			weather.append(",");

		}
		weather.append("]");
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException{
		for(Pair<String, Weather> par : lista)
		{
			Road r = map.getRoad(par.getFirst());
			if(r== null)
				throw new IllegalArgumentException("Excepcion en el metodo execute de setWeatherEvent");
			else
				r.setCondicionesAmbientales(par.getSecond());
		}

	}
	
	public String toString()
	{
		return "Set Weather ' " + weather.toString() + " ' ";
	}

}
