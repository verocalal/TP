package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}
	

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time = 1;
		if(data.has("data"))
		{
			time = (int)data.get("data");
		}
		return new MostCrowdedStrategy(time);
	}

}
