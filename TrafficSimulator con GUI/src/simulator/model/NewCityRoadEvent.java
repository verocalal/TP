package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

	
	
	public NewCityRoadEvent (int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) 
	{
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}


	@Override
	public Road createRoadObject(String id, Junction src, Junction dest, int velocidad, int co2Limit, int length,
			Weather tiempo) {
		CityRoad r = new CityRoad(id, src, dest, velocidad, co2Limit, length, tiempo);
		return r;
	}

	
	
	

}
