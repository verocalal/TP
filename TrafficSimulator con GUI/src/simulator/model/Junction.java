package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	private List<Road> carreterasEntrantes;
	private Map<Junction,Road> carreterasSalientes;
	private List<List<Vehicle>> ListaColas;
	private Map<Road,List<Vehicle>>Mapa_Carretera_Cola;
	private int semaforoVerde;// indice de la carretera con el semaforo en verde
	private int ultimoPasoCambioSemaforo;
	private LightSwitchingStrategy estrategia;
	private DequeuingStrategy estrategiaExtraer;
	private int X;
	private int Y;
	
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)  throws IllegalArgumentException{
		super(id);
			if(lsStrategy==null || dqStrategy==null || xCoor<0 || yCoor<0)
				throw new IllegalArgumentException("Excepcion en el constructor de Junction");
			else
			{
				this.estrategia=lsStrategy;
				this.estrategiaExtraer=dqStrategy;
				this.setX(xCoor);
				this.setY(yCoor);
				this.carreterasEntrantes= new ArrayList<Road>();
				this.carreterasSalientes = new HashMap<Junction, Road>();
				this.ListaColas = new ArrayList<List<Vehicle>>();
				this.Mapa_Carretera_Cola = new HashMap<Road, List<Vehicle>>();
				this.semaforoVerde=0;
				this.ultimoPasoCambioSemaforo=0;
			}

	}

	void addIncommingRoad(Road r) throws IllegalArgumentException
	{
		if(r.getDestino()==this)
		{
			this.carreterasEntrantes.add(r); // añado la carretera a la lista de carreteras entrantes
			if(!r.getListaVehiculos().isEmpty())
			{
				List<Vehicle> l = new ArrayList<Vehicle>(r.getListaVehiculos());
				this.ListaColas.add(l); // añado la lista a la lista de listas
				this.Mapa_Carretera_Cola.put(r, l);
			}
		}
		else
			throw new IllegalArgumentException("Excepcion en addIncomingRoad de Junction");
	
	}
	void addOutGoingRoad(Road r) throws IllegalArgumentException
	{
			Junction j = r.getDestino();
			if( r.getOrigen()==this) // si no hay otra carretera que vaya al cruce j
						this.carreterasSalientes.put(j, r);
			else
				throw new IllegalArgumentException("Excepcion en el addOutGoingRoad");

	}
	void enter(Vehicle v)
	{
		Road r = v.getRoad();
		if(this.Mapa_Carretera_Cola.containsKey(r)) // si en el mapa exite la carretera r
			this.Mapa_Carretera_Cola.get(r).add(v); // hago get a la lista y añado v
	}
	Road roadTo(Junction j)
	{
		Road r= null;
		if(this.carreterasSalientes.containsKey(j)) // ¿existe alguna que vaya al cruce j?
		{
			r=this.carreterasSalientes.get(j); // si existe damela
		}
		return r;
		
	}
	
	@Override
	void advance(int time) throws IllegalArgumentException {
		
		if(!this.Mapa_Carretera_Cola.isEmpty() && !this.Mapa_Carretera_Cola.get(this.semaforoVerde).isEmpty())
		{
			List<Vehicle> lista = new ArrayList<Vehicle>();
			lista = this.estrategiaExtraer.dequeue(this.ListaColas.get(this.semaforoVerde));
			System.out.println(this.semaforoVerde);
			for(int i=0;i<lista.size();i++)
				{
					lista.get(i).moveToNextRoad();
					this.ListaColas.get(this.semaforoVerde).remove(i);
				}
		}

		int indice = this.estrategia.chooseNextGreen(carreterasEntrantes, ListaColas, semaforoVerde, ultimoPasoCambioSemaforo, time);
		if(indice != this.semaforoVerde)
		{
			this.semaforoVerde=indice;
			this.ultimoPasoCambioSemaforo=time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject obj=new JSONObject();
		obj.put("id",this.getId());
		if(this.semaforoVerde==-1)
			obj.put("green","none");
		else
			obj.put("green",this.getSemaforoVerde());
		
		JSONObject entrantes= new JSONObject();
		for(int i=0;i<this.carreterasEntrantes.size();i++)
		{
			Road road= this.carreterasEntrantes.get(i);
			entrantes.put("road", road.getId());
			if(this.Mapa_Carretera_Cola.containsKey(road.getId()))
			{
				List<Vehicle> aux= new ArrayList<Vehicle>(this.Mapa_Carretera_Cola.get(road.getId()));
				if(!aux.isEmpty())
					for( Vehicle v : aux)
						entrantes.put("vehicles", v.getId());
			}
		}
		obj.put("queues",entrantes);
		return obj;

	}
	public int getSemaforoVerde() {
		return semaforoVerde;
	}
	public void setSemaforoVerde(int semaforoVerde) {
		this.semaforoVerde = semaforoVerde;
	}
	
	public List<Road> getIncomingRoads()
	{
		
		List<Road> incoming = Collections.unmodifiableList(new ArrayList<Road>(this.carreterasEntrantes));
		return incoming;
	}
	public Map<Road,List<Vehicle>> getMapaCarreteraCola()
	{
		return this.Mapa_Carretera_Cola;
	}
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

}
