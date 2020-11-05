package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2limit, int maxSpeed,
			Weather weather) {
		super(time, id, srcJun, destJunc, length, co2limit, maxSpeed, weather);
	}

	
	@Override
	public Road createRoadObject(String id, Junction src, Junction dest, int velocidad, int co2Limit, int length,
			Weather tiempo) {
		InterCityRoad r = new InterCityRoad(id, src, dest, velocidad, co2Limit, length, tiempo);
		return r;
	}

}
