package simulator.model;

public class InterCityRoad extends Road {

	

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		//(int)((100.0-x)/100.0)*tc)
		// tc= contaminacion total actual
		// x depende de Weather
		int tc=this.getContaminacionTotal();
		int x=0;
		switch(this.getCondicionesAmbientales()) 
		{
			case SUNNY: x=2; break;
			case CLOUDY: x=3; break;
			case RAINY: x=10; break;
			case WINDY:x=15; break;
			case STORM:x=20; break;
			default:
				break;
		}
		int reducir=(int)((100.0-x)/100.0)*tc;
		this.modificarContaminacion(-reducir);
	}

	@Override
	void updateSpeedLimit() 
	{
		if(this.getContaminacionTotal()>this.getLimiteContaminacion())
			{
				int nuevoLimite= (int) (this.getVelocidadMax()*0.5);
				this.setLimiteVelocidad(nuevoLimite);
			}
		else
			this.setLimiteVelocidad(this.getVelocidadMax());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v)
	{
		int velocidad;
		if(this.getCondicionesAmbientales()==Weather.STORM)
			velocidad=(int) (this.getLimiteVelocidad()*0.8);
		else
			velocidad=this.getLimiteVelocidad();
		return velocidad;
	}


}
