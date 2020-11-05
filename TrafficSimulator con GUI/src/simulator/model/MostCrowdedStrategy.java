package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) 
	{
		int a = -1;
		if(!qs.isEmpty())
		{
			if(roads.isEmpty())
				a = -1;
			else if(currGreen == -1) // si todos estan en rojo
			{
				int maxSize=0;
				int i;
				for(i=0;i<qs.size();i++)
				{
					ArrayList<Vehicle> aux= (ArrayList<Vehicle>) qs.get(i);
					if(aux.size()>maxSize)
						{
							maxSize=aux.size();
							a = i;
						}
				}
			}
			else if(currTime-lastSwitchingTime<timeSlot)
				a = currGreen;
			else 
			{
				//	utilizo dos booleanos por la casuistica del problema
				//	estare seguro de haber encontrado la cola mas larga(boolean encontrado)
				//	cuando haya dado una vuelta entera(boolean circular)
				boolean circular=false;
				boolean encontrado=false;
				int tamMax=-1;
				int indice = currGreen+1;
				while(!circular && !encontrado)
				{
					if(qs.get(indice).size()>tamMax)
					{
						tamMax = qs.get(indice).size();
							{
								a = indice;
								encontrado=true;
							}
					}
					if(indice == qs.size())// cuando lleguemos al final del recorrico circular volvemos al principio
						{
							indice=0;
							circular=true;
						}
				}
			}
		}
		return a;
	}

	public MostCrowdedStrategy(int ts)
	{
		this.timeSlot=ts;
	}
}
