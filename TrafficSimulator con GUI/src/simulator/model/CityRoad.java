package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,  Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int r;
		if(this.getCondicionesAmbientales()==Weather.STORM || this.getCondicionesAmbientales()==Weather.WINDY )
			r=10;
		else
			r=2;
		this.modificarContaminacion(-r);
		if(this.getContaminacionTotal()<0)
			this.setContaminacionTotal(0);
		
	}

	@Override
	void updateSpeedLimit() {
		// la velocidad límite no cambia, siempre es la velocidad máxima.	
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		//(int)(((11.0-f)/11.0)*s)
		int f=v.getGradoContaminacion();
		int s=this.getLimiteVelocidad();
		int a=(int)(((11.0-f)/11.0)*s);
		return a;
	}
	
}
