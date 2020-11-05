package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{

	private int time;
	private String id;
	private int velocidad;
	private int co2;
	private List<String> Itinerario;
	public NewVehicleEvent(int time,String id,int maxSpeed,int contClass,List<String>itinerario) {
		super(time);
		this.time=time;
		this.id=id;
		velocidad=maxSpeed;
		co2=contClass;
		Itinerario = new ArrayList<String>(itinerario);
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException{
			
		List<Junction> lista = new ArrayList<Junction>();
		for(int i=0;i<this.Itinerario.size();i++)
		{
			Junction j = map.getJunction(this.Itinerario.get(i));
			if(j == null) // si el cruce por el que quiere pasar el vehiculo no existe
				throw new IllegalArgumentException("Excepcion en new vehicle event");
			else
				lista.add(j);
		}
		Vehicle v = new Vehicle (id, velocidad, co2, lista);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	
	public String toString()
	{
		return "New Vehicle ' " + this.id + " ' ";
	}

}
