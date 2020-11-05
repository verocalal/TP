package simulator.model;

public class NewJunctionEvent extends Event {

	
	private Junction cruce;
	private String id;
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)  {
		super(time);
		cruce = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		this.id=id;
	}

	@Override
	void execute(RoadMap map) {
		
		map.addJunction(cruce);
		
	}
	
	public String toString()
	{
		return "New Junction ' " + this.id+ " ' ";
	}
}
