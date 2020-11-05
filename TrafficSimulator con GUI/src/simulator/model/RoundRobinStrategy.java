package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime)
	{
		int a = currGreen;
		if(currTime%5==0)
		{
			a = 0;
			if(roads.isEmpty())
				a=-1;
			else if(currGreen==-1)
				a= 0;
			else if(currTime-lastSwitchingTime<timeSlot)
				a= currGreen;
			else 
				a= currGreen+1%roads.size();
		}
		return a;
	}

	
	public RoundRobinStrategy(int ts) {
		this.timeSlot=ts;
	}
}
