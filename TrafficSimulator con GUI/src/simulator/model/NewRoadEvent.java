package simulator.model;

public abstract class NewRoadEvent extends Event{

	private String destino;
	private String origen;
	private int time;
	private String id;
	private int lenght;
	private int limite;
	private int velocidad;
	private Weather tiempo;
	NewRoadEvent(int time,String id,String srcJun,String destJunc,int length,int co2limit,int maxSpeed,Weather weather) {
		super(time);
		destino= destJunc;
		origen = srcJun;
		this.time= time;
		this.id=id;
		this.lenght= length;
		limite = co2limit;
		velocidad = maxSpeed;
		tiempo= weather;
	}

	@Override
	void execute(RoadMap map) throws IllegalArgumentException{
		
		Junction src = map.getJunction(origen);
		Junction dest = map.getJunction(destino);
		if(src == null || dest == null)
			throw new IllegalArgumentException("Excepcion en New city road even");
		else {
			Road r = createRoadObject(id,src,dest,velocidad,limite,lenght,tiempo);
			map.addRoad(r); 
			src.addOutGoingRoad(r);
			dest.addIncommingRoad(r);
		}
	}
	
	
	public abstract Road createRoadObject(String id, Junction src, Junction dest, int velocidad, int co2Limit, int length, Weather tiempo);

	public String toString()
	{
		return "New Road ' " + this.id + " ' ";
	}
}
