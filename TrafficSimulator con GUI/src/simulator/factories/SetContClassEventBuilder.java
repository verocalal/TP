package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContaminationClass;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		// la explicacion de como se lee JSONObject está en la clase "SetWeatherEventBuilder"
		
		int time = (int)data.get("time");
		List<Pair<String,Integer>> listaPares = new ArrayList<Pair<String,Integer>>();
		JSONArray pares = data.getJSONArray("info");
		
		for(int i=0;i<pares.length();i++)
		{
			JSONObject aux = pares.getJSONObject(i);
			Pair<String,Integer> auxiliar_par = new Pair<String,Integer>(aux.getString("vehicle"),aux.getInt("class"));
			listaPares.add(auxiliar_par);
		}
		
		return new SetContaminationClass(time,listaPares);
	}

}
