package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{

	protected int time;
	protected String id;
	protected String src;
	protected String dest;
	protected int length;
	protected int co2;
	protected int velMax;
	protected Weather w;
	public NewRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = (int)data.get("time");
		String id = (String)data.get("id");
		String src = (String)data.get("src");
		String dst = (String)data.get("dest");
		int lenght = (int)data.get("length");
		int co2 = (int)data.get("co2limit");
		int velMax = (int)data.get("maxspeed");
		Weather w = Weather.valueOf((String) data.get("weather"));
		
		this.time=time;
		this.id=id;
		this.src=src;
		this.dest= dst;
		this.length=lenght;
		this.co2= co2;
		this.velMax = velMax;
		this.w=w;
		return createTheRoad();
	}
	
	public abstract Event createTheRoad();

}
