package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	private List<Junction> itinerario;
	private int VelocidadMaxima;
	private int VelocidadActual;
	private VehicleStatus estado;
	private Road carretera;
	private int localizacion;
	private int gradoContaminacion; // entre 0 y 10
	private int contaminacionTotal; // el total durante el recorrido
	private int distanciaTotal;
	private int cruceActual;
	
	
	Vehicle(String id,int maxSpeed, int contClass, List<Junction> itinerario) { // contClass es contaminacion
		super(id);
		//comprobar que los argumentos son validos
		// si no lo son,excepcion
		// hoja 5 practica1.pdf para condiciones
		if(maxSpeed>0 && contClass>=0 && contClass<=10 && itinerario.size()>=2)
		{
			this.itinerario=Collections.unmodifiableList(new ArrayList<>(itinerario));
			this.VelocidadMaxima=maxSpeed;
			this.gradoContaminacion=contClass;
			this.carretera=null;
			this.cruceActual=-1;
			this.estado = VehicleStatus.PENDING;
		}
		else
			throw new IllegalArgumentException("Error en constructor de vehiculo");
	}

	
	void setSpeed(int s) throws IllegalArgumentException
	{
		if(s<0)
			throw new IllegalArgumentException("La velocidad del vehiculo es negativa\n");
		else
			this.VelocidadActual=Math.min(s,this.VelocidadMaxima);
	}
	
	void setContaminacionClass(int c) throws IllegalArgumentException
	{
		if(c<0 || c>10)
			throw new IllegalArgumentException("EL valor de contaminaicon no es un valor entre 0 y 10\n");
		else
			this.gradoContaminacion=c;

	}
	
	void moveToNextRoad() throws IllegalArgumentException
	{
		if(this.estado != VehicleStatus.PENDING && this.estado != VehicleStatus.WAITING)
				throw new IllegalArgumentException("Excepcion en moveToNextRoad de Vehicle");
		else
		{
			if(this.estado== VehicleStatus.PENDING) // en el primer cruce
			{
				Road r = this.itinerario.get(0).roadTo(this.itinerario.get(1));
				this.carretera=r;
				r.enter(this);
				this.cruceActual+=2;
				this.estado = VehicleStatus.TRAVELING;
			}
			else // en el resto de cruces
			{
				Junction j = this.carretera.getDestino();
				if(this.carretera!=null)
					this.carretera.exit(this);
				this.localizacion = 0;
				this.VelocidadActual=0;
				this.cruceActual++;
				Junction siguiente = this.itinerario.get(this.cruceActual);
				Road r = j.roadTo(siguiente);
				if(siguiente == null)
					{
						this.estado=VehicleStatus.ARRIVED;
					}
				else
					this.estado=VehicleStatus.TRAVELING;
				r.enter(this);
			}
		}
	}
	
	
	@Override
	void advance(int time) throws IllegalArgumentException { 
		if(this.estado == VehicleStatus.TRAVELING)
		{
			int antigua=this.localizacion;
			localizacion = Math.min(this.localizacion+this.VelocidadActual, this.carretera.getLonguitud());
			int contaminacion= this.gradoContaminacion*(localizacion-antigua);
			this.setContaminacionTotal(this.getContaminacionTotal() + contaminacion);
			this.carretera.addContaminacion(contaminacion);
			if(localizacion>=this.carretera.getLonguitud()) // he llegado al final
			{
				Junction j = this.carretera.getDestino();
				if(j!=this.itinerario.get(this.itinerario.size()-1))
				{
					j.enter(this);
					this.estado = VehicleStatus.WAITING;
				}
				else
				{
					this.estado = VehicleStatus.ARRIVED;
					this.carretera.exit(this);
				}

			}
			this.distanciaTotal+=localizacion;
		}
	}

	@Override
	public JSONObject report() {
		
		JSONObject obj = new JSONObject();
		obj.put("id", this.getId());
		obj.put("speed", this.getVelActual());
		obj.put("distance", this.getDistTotal());
		obj.put("co2",this.getContaminacionTotal());
		obj.put("class",3);
		obj.put("status", this.getEstado());
		if(this.estado != VehicleStatus.ARRIVED && this.estado != VehicleStatus.PENDING)
		{
			obj.put("road", this.carretera.getId()); //TODO
			obj.put("location", this.localizacion);
		}
		return obj;
		
	}
	public int getLocalizacion()
	{
		return this.localizacion;
	}
	public void setLocalizacion(int l)
	{
		this.localizacion=l;
	}
	public int getVelActual()
	{
		return this.VelocidadActual;
	}
	public void setVelActual(int va)
	{
		this.VelocidadActual=va;
	}
	public void setDistTotal(int d)
	{
		this.distanciaTotal+=d;
	}
	public int getDistTotal()
	{
		return this.distanciaTotal;
	}
	public int getContaminacionTotal() {
		return contaminacionTotal;
	}
	public void setContaminacionTotal(int contaminacionTotal) {
		if(this.contaminacionTotal < Integer.MAX_VALUE && this.contaminacionTotal >= 0)
			this.contaminacionTotal += contaminacionTotal;

	}
	public VehicleStatus getEstado()
	{
		return this.estado;
	}
	public void setEstado(VehicleStatus vs)
	{
		this.estado=vs;
	}
	public Road getRoad()
	{
		return this.carretera;
	}
	public void setCarretera(Road r)
	{
		this.carretera=r;
	}
	public int getCruceActual()
	{
		return this.cruceActual;
	}
	public int getGradoContaminacion()
	{
		return this.gradoContaminacion;
	}
	public List<Junction> getItinerario()
	{
		return this.itinerario;
	}
	public int getVelMax()
	{
		return this.VelocidadMaxima;
	}
	@Override
	public int compareTo(Vehicle o) { 
		if(this.localizacion==o.localizacion)  
			return 0;  
		else if(this.localizacion>o.localizacion)  
			return 1;  
		else  
			return -1;  
		}
	
}
