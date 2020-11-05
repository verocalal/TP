package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	
	private List<Junction> cruces;
	private List<Road> carreteras;
	private List<Vehicle> vehiculos;
	
	private Map<String,Junction>mapaCruces;
	private Map<String,Vehicle> mapaVehiculos;
	private Map<String,Road> mapaCarreteras;
	
	RoadMap(List<Junction> lista_cruces, List<Road> lista_carreteras, List<Vehicle> lista_vehiculos,
			Map<String, Junction> mapa_cruces, Map<String, Road> mapa_carreteras, Map<String, Vehicle> mapa_vehiculos) { 
		this.carreteras = lista_carreteras;
		this.cruces = lista_cruces;
		this.vehiculos = lista_vehiculos;
		this.mapaCarreteras = mapa_carreteras;
		this.mapaCruces = mapa_cruces;
		this.mapaVehiculos = mapa_vehiculos;
	}
	void addJunction(Junction j)
	{
		this.cruces.add(j);
		this.mapaCruces.put(j.getId(),j);
	}
	void addRoad(Road r)
	{
		this.carreteras.add(r);
		this.mapaCarreteras.put(r.getId(),r);
	}
	void addVehicle(Vehicle v)
	{
		this.vehiculos.add(v);
		this.mapaVehiculos.put(v.getId(),v);
	}
	public Junction getJunction(String id)
	{
		Junction cruce=null;
		for(int i=0;i<cruces.size();i++)
			if(cruces.get(i).getId().equals(id))
				cruce=cruces.get(i);
		return cruce;
	}
	public Road getRoad(String id)
	{
		Road carretera=null;
		for(Road r : this.carreteras)
			if(r.getId().equals(id))
				carretera=r;
		return carretera;
	}
	public Vehicle getVehicle(String id)
	{
		Vehicle v=null;
		for(int i=0;i<this.vehiculos.size();i++)
		{
			if(id.equals(this.vehiculos.get(i).getId()))
				v=this.vehiculos.get(i);
		}
		return v;
	}
	public List<Junction> getJunctions()
	{
		List<Junction> lista = Collections.unmodifiableList(new ArrayList<>(cruces));
		return lista;
		
	}
	public List<Road> getRoads()
	{
		List<Road> lista = Collections.unmodifiableList(new ArrayList<>(carreteras));
		return lista;
	}
	public List<Vehicle> getVehicles()
	{
		List<Vehicle> lista = Collections.unmodifiableList(new ArrayList<>(vehiculos));
		return lista;
	}
	void reset()
	{
		this.carreteras.clear();
		this.cruces.clear();
		this.vehiculos.clear();
		this.mapaCarreteras.clear();
		this.mapaVehiculos.clear();
		this.mapaCruces.clear();
	}
	public JSONObject report()
	{
		JSONObject obj = new JSONObject();// el que devuelvo
		
		
		JSONArray roads = new JSONArray();
		for(int i=0;i<this.carreteras.size();i++)
		{
			JSONObject o = this.carreteras.get(i).report();
			roads.put(o);
		}
		obj.put("Roads", roads);
		

		
		JSONArray vehiculos = new JSONArray();
		for(int i=0;i<this.vehiculos.size();i++)
		{
			JSONObject o = this.vehiculos.get(i).report();
			vehiculos.put(o);
		}
		obj.put("Vehiculos", vehiculos); 
		
		
		JSONArray cruces = new JSONArray();
		for(int i=0;i<this.cruces.size();i++)
		{
			JSONObject o = this.cruces.get(i).report();
			cruces.put(o);
		}
		obj.put("Juctions", cruces);
		
		
		return obj;
	}
	
	
	
	
	
	
}
