package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	Factory<LightSwitchingStrategy> semaforo;
	Factory<DequeuingStrategy> extraer;
	
	public NewJunctionEventBuilder (Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory)
	{
		super("new_junction");
		semaforo = lssFactory;
		extraer = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = (int)data.get("time");
		String id = (String)data.get("id");
		
		JSONArray list = data.getJSONArray("coor");
		int x = (int) list.get(0);
		int y = (int) list.get(1);

		
		JSONObject aux = (JSONObject) data.get("ls_strategy");
		JSONObject aux2 = (JSONObject) data.get("dq_strategy");

		LightSwitchingStrategy estrategia = semaforo.createInstance(aux);
		DequeuingStrategy estrategia2 = extraer.createInstance(aux2);
		return new NewJunctionEvent(time, id, estrategia,estrategia2,x,y);
	}

}
