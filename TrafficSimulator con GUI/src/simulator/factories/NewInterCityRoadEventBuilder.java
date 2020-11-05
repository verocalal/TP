package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	public Event createTheRoad() {
		
		return new NewInterCityRoadEvent(time, id, src,dest,length,co2,velMax,w);
	}

}
