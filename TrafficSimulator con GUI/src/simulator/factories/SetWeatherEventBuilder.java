package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// la informacion viene en el valor de la clave "info"
		// ese valor es un JSONArray el cual contiene n JSONOBjects
		// cada JSONObject es un par que tiene una clave "road" y una clave "weather" con 
		// la informacion util que queremos almacenar
		int time = (int)data.get("time");
		
		List<Pair<String,Weather>> listaPares = new ArrayList<Pair<String,Weather>>();
		JSONArray pares = data.getJSONArray("info"); // de aqui sacaremos la info
		
		
		for(int i=0;i<pares.length();i++)
		{
			JSONObject aux = pares.getJSONObject(i);// JSONObject auxiliar del que vamos a extraer los valores de sus claves "road" y "weather"
			Pair<String,Weather> par_auxiliar = new Pair<String,Weather>(aux.getString("road"), Weather.valueOf(aux.getString("weather")));
			listaPares.add(par_auxiliar);
		}
		
		return new SetWeatherEvent(time,listaPares);
	}

}
