package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;


public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}


	@Override
	public Event createTheRoad() {
		
		return new NewCityRoadEvent(time, id, src,dest,length,co2,velMax,w);
	}


}
