package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = (int) data.get("time");
		String id = (String) data.get("id");
		int maxVel = (int) data.get("maxspeed");
		int clase = (int) data.get("class");
		List<String> itinerario= new ArrayList<String>();
		for(Object j : data.getJSONArray("itinerary"))
			itinerario.add((String) j);
		
		
		return new NewVehicleEvent(time,id,maxVel,clase,itinerario);
	}

}
