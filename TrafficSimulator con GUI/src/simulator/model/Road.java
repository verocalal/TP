package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction origen; // cruce de origen
	private Junction destino;
	private int longuitud;
	private int velocidadMax;
	private int limiteVelocidad;
	private int LimiteContaminacion;
	private Weather condicionesAmbientales;
	private int contaminacionTotal; // Acumulado de contaminacion en la carretera
	private List<Vehicle> vehiculos;//ordenada por localizacion de vehiculos(orden descendente)

	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, 
			Weather weather) throws IllegalArgumentException 
	{ 
		super(id);
		if(maxSpeed>0 && contLimit>0 && length>0  && srcJunc!= null && destJunc != null && weather != null)
		{
			this.velocidadMax=maxSpeed;
			this.limiteVelocidad=this.velocidadMax;
			this.LimiteContaminacion=contLimit;
			this.longuitud=length;
			this.origen=srcJunc;
			this.destino=destJunc;
			this.condicionesAmbientales=weather;
			this.vehiculos = new ArrayList<Vehicle>();
			this.contaminacionTotal = 0;
			srcJunc.addOutGoingRoad(this);
			destJunc.addIncommingRoad(this);
		}
		else
			throw new IllegalArgumentException("Argumentos en constructora de Road invalidos!\n");
	}
	
	void enter(Vehicle v) throws IllegalArgumentException
	{
		//añade el vehiculo a la lista
		// lanza excepcion si v.loc!=0 && v.velocidad!=0
		if(v.getLocalizacion()==0 && v.getVelActual()==0)
			{
				this.vehiculos.add(v);
				v.setCarretera(this);
			}
		else
			throw new IllegalArgumentException("Las caracteristicas del vehiulo no son buenas\n");
	}
	void exit(Vehicle v)
	{
		this.vehiculos.remove(v);
	}
	
	void setWheather(Weather w) throws IllegalArgumentException
	{
		if(w== null)
		{
			throw new IllegalArgumentException("EL tiempo atmosferico es null!!\n");
		}
		else
			this.setCondicionesAmbientales(w);
	}
	
	
	@Override
	void advance(int time) throws IllegalArgumentException { 
		
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(int i=0;i<this.vehiculos.size();i++)
		{
			if(this.vehiculos.get(i).getEstado() == VehicleStatus.TRAVELING)
			{
				int vel=this.calculateVehicleSpeed(this.vehiculos.get(i));
				this.vehiculos.get(i).setSpeed(vel);
				this.vehiculos.get(i).advance(time);
			}
			else
				this.vehiculos.get(i).moveToNextRoad();
		}
		Collections.sort(this.vehiculos);
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("speedLimit",this.getLimiteVelocidad());
		obj.put("id",this.getId());
		obj.put("weather", this.getCondicionesAmbientales());
		obj.put("co2",this.getContaminacionTotal());
		List<Vehicle> lista = this.getListaVehiculos();
		JSONArray aux = new JSONArray();
		for(Vehicle v : lista)
			aux.put(v);
		obj.put("vehicles", aux); // nueva lista copiada de la original
		
		
		return obj;
	}
	////////////////////////////////////////////// METODOS ABSTRACTOS 
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	///////////////////////////////////////////////
	public int getLonguitud() {
		return longuitud;
	}

	public void addContaminacion(int c) throws IllegalArgumentException
	{
		if(c<0)
			throw new IllegalArgumentException("Estas añadiendo contaminacion negativa\n");
		else
			this.setContaminacionTotal(this.getContaminacionTotal() + c);
	}

	public Junction getOrigen() {
		return origen;
	}

	public void setOrigen(Junction origen) {
		this.origen = origen;
	}

	public Junction getDestino() {
		return destino;
	}

	public void setDestino(Junction destino) {
		this.destino = destino;
	}

	public int getVelocidadMax() {
		return velocidadMax;
	}

	public void setVelocidadMax(int velocidadMax) {
		this.velocidadMax = velocidadMax;
	}

	public int getLimiteVelocidad() {
		return limiteVelocidad;
	}

	public void setLimiteVelocidad(int limiteVelocidad) {
		this.limiteVelocidad = limiteVelocidad;
	}

	public int getLimiteContaminacion() {
		return LimiteContaminacion;
	}

	public void setLimiteContaminacion(int alarmaContaminacionExcesiva) {
		this.LimiteContaminacion = alarmaContaminacionExcesiva;
	}

	public Weather getCondicionesAmbientales() {
		return condicionesAmbientales;
		
	}

	public void setCondicionesAmbientales(Weather condicionesAmbientales) {
		this.condicionesAmbientales = condicionesAmbientales;
	}

	public int getContaminacionTotal() {
		return contaminacionTotal;
	}

	public void setContaminacionTotal(int contaminacionTotal) {
		this.contaminacionTotal = contaminacionTotal;
	}
	public List<Vehicle> getListaVehiculos()
	{
		List<Vehicle> lista = Collections.unmodifiableList(new ArrayList<Vehicle>(this.vehiculos));
		return lista;
	}
	public void modificarContaminacion(int c)
	{
		this.contaminacionTotal+=c;
	}
}
