package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	
	private RoadMap mapaCarreteras;
	private SortedArrayList<Event> listaEventos;
	private int tiempo;
	private int tiempoActual;
	private List<TrafficSimObserver> observadores;
	
	public TrafficSimulator()
	{
		this.listaEventos = new SortedArrayList<Event>();
		this.mapaCarreteras= new RoadMap(new ArrayList<Junction>(),new ArrayList<Road>(),new ArrayList<Vehicle>(),
				new HashMap<String,Junction>(),new HashMap<String,Road>(),new HashMap<String,Vehicle>());
		this.tiempo= 0 ;
		this.tiempoActual = 0;
		observadores = new ArrayList<TrafficSimObserver>();
	}
	public void setTiempo(int t)
	{
		this.tiempo=t;
	}
	public void addEvent(Event e)
	{
		this.listaEventos.add(e);
		for(int i=0;i<this.observadores.size();i++)
			observadores.get(i).onEventAdded(mapaCarreteras, listaEventos, e, this.tiempoActual);
	}
	public void advance() 
	{
		this.tiempoActual++;
		
		for(int i=0;i<this.observadores.size();i++)//on advance start
			this.observadores.get(i).onAdvanceStart(mapaCarreteras, listaEventos, tiempoActual);
		
		List<Event> auxiliar = new ArrayList<Event>();
		for(int i=0;i<listaEventos.size();i++)
		{
			if(listaEventos.get(i).getTime()==this.tiempoActual)
			{
				listaEventos.get(i).execute(mapaCarreteras);
				auxiliar.add(listaEventos.get(i));
			}
		}
		
		// es necesario que se borren de esta forma, sin esto quedan eventos sin ejecutar
		for(int i =0;i<auxiliar.size();i++) 
			listaEventos.remove(auxiliar.get(i));
		
		List<Junction> cruces = mapaCarreteras.getJunctions();
		List<Road> carreteras = mapaCarreteras.getRoads();

		try {
		for(int i=0;i<cruces.size();i++)
			cruces.get(i).advance(tiempo);
		for(int i=0;i<carreteras.size();i++)
			carreteras.get(i).advance(tiempo);
		}catch(Exception e) {
			for(int i=0;i<this.observadores.size();i++)//onError
				this.observadores.get(i).onError(e.getMessage());
		}
		for(int i=0;i<this.observadores.size();i++)//on advance end
			this.observadores.get(i).onAdvanceEnd(mapaCarreteras, listaEventos, tiempoActual);
		
	}
	public void reset()
	{
		this.mapaCarreteras.reset();
		this.listaEventos.clear();
		this.tiempo=0;
		for(TrafficSimObserver o : this.observadores)
			o.onReset(mapaCarreteras, listaEventos, tiempoActual);
	}
	public JSONObject report()
	{
		JSONObject j= new JSONObject();
		JSONObject aux= new JSONObject();
		
		j.put("time", tiempoActual);
		
		j.put("state", this.mapaCarreteras.report());
		
		return j;
	}
	public void addObserver(TrafficSimObserver o) {
		this.observadores.add(o);
		//o.onRegister(this.mapaCarreteras, evento(?), this.tiempoActual);
		//onRegister
		
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observadores.remove(o);
		
	}
	public List<Event> getListaEventos()
	{
		return this.listaEventos;
	}
	public RoadMap getMapaCarreteras()
	{
		return this.mapaCarreteras;
	}

}
